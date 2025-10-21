import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';

// Mock lucide-react to avoid dependency issues
jest.mock('lucide-react', () => ({
  User: () => <div data-testid="user-icon" />
}));

// Import the component after mocking its dependencies
import AboutUs from './About';

describe('AboutUs Component', () => {
  beforeEach(() => {
    render(<AboutUs />);
  });

  test('renders the About Us header', () => {
    const headerElement = screen.getByText(/About Us/i);
    expect(headerElement).toBeInTheDocument();
  });

  test('renders the Meet the Team section header', () => {
    const teamSectionHeader = screen.getByText(/Meet the Team!/i);
    expect(teamSectionHeader).toBeInTheDocument();
  });

  test('renders all team members', () => {
    expect(screen.getByText('Talia Crockett')).toBeInTheDocument();
    expect(screen.getByText('Daniel Boyce')).toBeInTheDocument();
    expect(screen.getByText('Ethan Hillman')).toBeInTheDocument();
    expect(screen.getByText('Calvin Robinson')).toBeInTheDocument();
    expect(screen.getByText('Joy Brown')).toBeInTheDocument();
  });
});
