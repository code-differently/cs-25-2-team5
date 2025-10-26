import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import { Event } from './Event';

const mockEvent = {
  id: '123',
  title: 'Sample Event',
  description: 'This is a sample event description.',
  location: 'Sample Location',
  organizer: { name: 'John Doe' },
  startTime: '10:00 AM',
  imageUrl: '/sample-image.jpg',
};

describe('Event component', () => {
  it('renders event details and image', () => {
    render(
      <MemoryRouter>
        <Event {...mockEvent} />
      </MemoryRouter>
    );
    expect(screen.getByText('Sample Event')).toBeInTheDocument();
    expect(screen.getByText('Sample Location')).toBeInTheDocument();
    expect(screen.getByText('John Doe • 10:00 AM')).toBeInTheDocument();
    expect(screen.getByAltText('Sample Event')).toHaveAttribute('src', '/sample-image.jpg');
  });

  it('uses default image if imageUrl is missing', () => {
    const eventWithoutImage = { ...mockEvent, imageUrl: undefined };
    render(
      <MemoryRouter>
        <Event {...eventWithoutImage} />
      </MemoryRouter>
    );
    expect(screen.getByAltText('Sample Event')).toHaveAttribute('src', '/default-event-image.jpg');
  });

  it('links to the correct event page', () => {
    render(
      <MemoryRouter>
        <Event {...mockEvent} />
      </MemoryRouter>
    );
    const link = screen.getByRole('link');
    expect(link).toHaveAttribute('href', '/event/123');
  });
});
