import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import userEvent from '@testing-library/user-event';
import Login from './Login';

window.alert = jest.fn(); // Mock alert function
describe('Login Page', () => {
    // Test to check if the login form renders correctly
    it('renders login form', () => {
        render(
            <MemoryRouter>
                <Login />
            </MemoryRouter>
        );
        expect(screen.getByLabelText(/email/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/password/i)).toBeInTheDocument();
    });
    it('should allow typing into input fields', async () => {
      render(<Login />);
      const emailInput = screen.getByLabelText(/email/i);
      const passwordInput = screen.getByLabelText(/password/i);

      await userEvent.type(emailInput, 'testuser@example.com');
      await userEvent.type(passwordInput, 'testpassword');

      expect(emailInput).toHaveValue('testuser@example.com');
      expect(passwordInput).toHaveValue('testpassword');
    });
    it('submits the form with correct data', async () => {
      render(<Login />);
      const emailInput = screen.getByLabelText(/email/i);
      const passwordInput = screen.getByLabelText(/password/i);
      const submitButton = screen.getByRole('button', { name: /Sign In/i });

      await userEvent.type(emailInput, 'testuser@example.com');
      await userEvent.type(passwordInput, 'testpassword');
      await userEvent.click(submitButton);
      // Here you would typically check for a mock function call or state change
    });
    it('should navigate to signup page on link click', () => {
      render(
        <MemoryRouter initialEntries={['/login']}>
          <Login />
        </MemoryRouter>
      );
      const signupLink = screen.getByRole('link', { name: /Sign Up/i });
      userEvent.click(signupLink); // Check that the link's href is correct
      expect(signupLink).toHaveAttribute('href', '/signup');
    });
});