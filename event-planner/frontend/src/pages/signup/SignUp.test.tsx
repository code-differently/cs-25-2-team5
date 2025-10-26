import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { BrowserRouter } from 'react-router-dom';
import SignUpPage from './SignUp';

// Mock the useSignUp hook from @clerk/clerk-react
jest.mock('@clerk/clerk-react', () => ({
  useSignUp: () => ({
    isLoaded: true,
    signUp: { create: jest.fn().mockResolvedValue({ status: 'complete', createdSessionId: 'dummy-session' }) },
    setActive: jest.fn(),
  }),
}));

describe('SignUpPage Component', () => {
  beforeEach(() => {
    render(
      <BrowserRouter>
        <SignUpPage />
      </BrowserRouter>
    );
  });

  // This test was failing - updated to use getAllByText and find the h1
  test('renders sign up page title', () => {
    // Get all elements with "Sign Up" text and find the one that's an h1
    const allSignUpElements = screen.getAllByText(/Sign Up/i);
    const headingElement = allSignUpElements.find(
      element => element.tagName.toLowerCase() === 'h1'
    );
    
    expect(headingElement).toBeInTheDocument();
    expect(headingElement).toHaveClass('signup-title');
  });

  test('renders form fields', () => {
    expect(screen.getByLabelText(/First name/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Last name/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Email/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/^Password$/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Confirm Password/i)).toBeInTheDocument();
  });

  test('shows login link for existing users', () => {
    const loginText = screen.getByText(/Already have an account/i);
    expect(loginText).toBeInTheDocument();
    
    const loginLink = screen.getByRole('link', { name: /Log in/i });
    expect(loginLink).toBeInTheDocument();
    expect(loginLink.getAttribute('href')).toBe('/login');
  });

  test('shows password requirements help text', () => {
    const helpText = screen.getByText(/Password must be at least 8 characters long/i);
    expect(helpText).toBeInTheDocument();
  });
});
