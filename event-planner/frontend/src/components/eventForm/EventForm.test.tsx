import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import EventForm from './EventForm';
import { useUser } from '@clerk/clerk-react';

// Mock Clerk
jest.mock('@clerk/clerk-react', () => ({
  useUser: jest.fn(),
}));

// Mock fetch
const mockFetch = jest.fn();
global.fetch = mockFetch;



// Mock window.location
Object.defineProperty(window, 'location', {
  value: {
    href: '',
  },
  writable: true,
});

// Mock alert
global.alert = jest.fn();

describe('EventForm Component', () => {
  const mockUser = {
    id: 'clerk_user_123',
    name: 'John Doe',
    email: 'john@example.com'
  };

  const mockBackendUser = {
    id: 24,
    name: 'John Doe',
    email: 'john@example.com'
  };

  beforeEach(() => {
    // Reset all mocks
    jest.clearAllMocks();
    
    // Mock successful Clerk user
    (useUser as jest.Mock).mockReturnValue({
      isSignedIn: true,
      user: mockUser
    });

    // Mock successful backend user fetch
    mockFetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockBackendUser,
    });
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  describe('Component Rendering', () => {
    it('should render the form with all required fields', async () => {
      render(<EventForm />);

      // Wait for user fetch to complete
      await waitFor(() => {
        expect(screen.getByLabelText(/title/i)).toBeInTheDocument();
      });

      expect(screen.getByLabelText(/description/i)).toBeInTheDocument();
      expect(screen.getByLabelText(/location/i)).toBeInTheDocument();
      expect(screen.getByLabelText(/date/i)).toBeInTheDocument();
      expect(screen.getByLabelText(/time/i)).toBeInTheDocument();
      expect(screen.getByLabelText(/image url/i)).toBeInTheDocument();
      expect(screen.getByLabelText(/visibility/i)).toBeInTheDocument();
      expect(screen.getByRole('button', { name: /create event/i })).toBeInTheDocument();
    });

    it('should have correct form header', () => {
      render(<EventForm />);
      expect(screen.getByText('Create an Event')).toBeInTheDocument();
    });
  });

  describe('Authentication', () => {
    it('should redirect when user is not signed in', () => {
      (useUser as any).mockReturnValue({
        isSignedIn: false,
        user: null
      });

      render(<EventForm />);

      expect(global.alert).toHaveBeenCalledWith('You must be signed in to create an event.');
      expect(window.location.href).toBe('/login');
    });

    it('should fetch backend user on component mount', async () => {
      render(<EventForm />);

      await waitFor(() => {
        expect(mockFetch).toHaveBeenCalledWith(
          'http://localhost:8080/api/v1/users/clerk/clerk_user_123'
        );
      });
    });
  });

  describe('Form Validation', () => {
    it('should show validation error when required fields are missing', async () => {
      const user = userEvent.setup();
      render(<EventForm />);

      // Wait for component to load
      await waitFor(() => {
        expect(screen.getByRole('button', { name: /create event/i })).toBeInTheDocument();
      });

      // Try to submit empty form
      await user.click(screen.getByRole('button', { name: /create event/i }));

      expect(global.alert).toHaveBeenCalledWith(
        'Please fill in all required fields and ensure you\'re logged in.'
      );
    });

    it('should validate that all required fields are filled', async () => {
      const user = userEvent.setup();
      render(<EventForm />);

      // Wait for component to load
      await waitFor(() => {
        expect(screen.getByLabelText(/title/i)).toBeInTheDocument();
      });

      // Fill only some fields
      await user.type(screen.getByLabelText(/title/i), 'Test Event');
      await user.type(screen.getByLabelText(/description/i), 'Test Description');
      // Leave location, date, time empty

      await user.click(screen.getByRole('button', { name: /create event/i }));

      expect(global.alert).toHaveBeenCalledWith(
        'Please fill in all required fields and ensure you\'re logged in.'
      );
    });
  });

  describe('Form Submission', () => {
    beforeEach(() => {
      // Mock successful event creation
      mockFetch
        .mockResolvedValueOnce({
          ok: true,
          json: async () => mockBackendUser,
        })
        .mockResolvedValueOnce({
          ok: true,
          json: async () => ({
            id: 53,
            title: 'Test Event',
            description: 'Test Description',
            startTime: '2025-10-26T14:30:00',
            eventType: 'Community',
            organizer: mockBackendUser,
            address: 'Test Location',
            guests: []
          }),
        });
    });

    it('should submit form with correct data when all fields are filled', async () => {
      const user = userEvent.setup();
      render(<EventForm />);

      // Wait for component to load
      await waitFor(() => {
        expect(screen.getByLabelText(/title/i)).toBeInTheDocument();
      });

      // Fill all required fields
      await user.type(screen.getByLabelText(/title/i), 'Test Event');
      await user.type(screen.getByLabelText(/description/i), 'Test Description');
      await user.type(screen.getByLabelText(/location/i), 'Test Location');
      await user.type(screen.getByLabelText(/date/i), '2025-10-26');
      await user.type(screen.getByLabelText(/time/i), '14:30');
      await user.type(screen.getByLabelText(/image url/i), 'http://example.com/image.jpg');

      // Submit form
      await user.click(screen.getByRole('button', { name: /create event/i }));

      // Wait for submission
      await waitFor(() => {
        expect(mockFetch).toHaveBeenCalledWith(
          'http://localhost:8080/api/v1/users/24/events',
          {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              title: 'Test Event',
              description: 'Test Description',
              isPublic: true,
              startTime: '2025-10-26T14:30:00',
              address: 'Test Location',
            }),
          }
        );
      });

      expect(global.alert).toHaveBeenCalledWith('Event created successfully!');
    });

    it('should handle form submission failure', async () => {
      const user = userEvent.setup();
      
      // Mock failed event creation
      mockFetch
        .mockResolvedValueOnce({
          ok: true,
          json: async () => mockBackendUser,
        })
        .mockResolvedValueOnce({
          ok: false,
          status: 400,
          text: async () => 'Bad Request',
        });

      render(<EventForm />);

      // Wait for component to load and fill form
      await waitFor(() => {
        expect(screen.getByLabelText(/title/i)).toBeInTheDocument();
      });

      await user.type(screen.getByLabelText(/title/i), 'Test Event');
      await user.type(screen.getByLabelText(/description/i), 'Test Description');
      await user.type(screen.getByLabelText(/location/i), 'Test Location');
      await user.type(screen.getByLabelText(/date/i), '2025-10-26');
      await user.type(screen.getByLabelText(/time/i), '14:30');
      await user.type(screen.getByLabelText(/image url/i), 'http://example.com/image.jpg');

      await user.click(screen.getByRole('button', { name: /create event/i }));

      await waitFor(() => {
        expect(global.alert).toHaveBeenCalledWith('Failed to create event: 400 - Bad Request');
      });
    });

    it('should reset form after successful submission', async () => {
      const user = userEvent.setup();
      render(<EventForm />);

      // Wait for component to load and fill form
      await waitFor(() => {
        expect(screen.getByLabelText(/title/i)).toBeInTheDocument();
      });

      const titleInput = screen.getByLabelText(/title/i) as HTMLInputElement;
      const descriptionInput = screen.getByLabelText(/description/i) as HTMLTextAreaElement;
      const locationInput = screen.getByLabelText(/location/i) as HTMLInputElement;
      const dateInput = screen.getByLabelText(/date/i) as HTMLInputElement;
      const timeInput = screen.getByLabelText(/time/i) as HTMLInputElement;

      await user.type(titleInput, 'Test Event');
      await user.type(descriptionInput, 'Test Description');
      await user.type(locationInput, 'Test Location');
      await user.type(dateInput, '2025-10-26');
      await user.type(timeInput, '14:30');

      await user.click(screen.getByRole('button', { name: /create event/i }));

      // Wait for successful submission
      await waitFor(() => {
        expect(global.alert).toHaveBeenCalledWith('Event created successfully!');
      });

      // Check that form fields are reset
      expect(titleInput.value).toBe('');
      expect(descriptionInput.value).toBe('');
      expect(locationInput.value).toBe('');
      expect(timeInput.value).toBe('');
    });
  });

  describe('Visibility Selection', () => {
    it('should change visibility when dropdown is selected', async () => {
      const user = userEvent.setup();
      render(<EventForm />);

      await waitFor(() => {
        expect(screen.getByLabelText(/visibility/i)).toBeInTheDocument();
      });

      const visibilitySelect = screen.getByLabelText(/visibility/i) as HTMLSelectElement;
      
      expect(visibilitySelect.value).toBe('public');
      
      await user.selectOptions(visibilitySelect, 'private');
      
      expect(visibilitySelect.value).toBe('private');
    });
  });

  describe('Date Time Formatting', () => {
    it('should format date and time correctly for API', async () => {
      const user = userEvent.setup();
      render(<EventForm />);

      await waitFor(() => {
        expect(screen.getByLabelText(/title/i)).toBeInTheDocument();
      });

      // Fill form with specific date/time
      await user.type(screen.getByLabelText(/title/i), 'Test Event');
      await user.type(screen.getByLabelText(/description/i), 'Test Description');
      await user.type(screen.getByLabelText(/location/i), 'Test Location');
      await user.type(screen.getByLabelText(/date/i), '2025-12-25');
      await user.type(screen.getByLabelText(/time/i), '09:30');
      await user.type(screen.getByLabelText(/image url/i), 'http://example.com/image.jpg');

      await user.click(screen.getByRole('button', { name: /create event/i }));

      await waitFor(() => {
        expect(mockFetch).toHaveBeenCalledWith(
          expect.any(String),
          expect.objectContaining({
            body: JSON.stringify({
              title: 'Test Event',
              description: 'Test Description',
              isPublic: true,
              startTime: '2025-12-25T09:30:00', // Check correct formatting
              address: 'Test Location',
            }),
          })
        );
      });
    });
  });
});
