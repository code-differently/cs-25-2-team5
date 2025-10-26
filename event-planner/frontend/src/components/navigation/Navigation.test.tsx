import React from 'react';
import { render, screen } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import '@testing-library/jest-dom';
import Navigation from './Navigation';

// Mock Clerk components and hooks
jest.mock('@clerk/clerk-react', () => {
  return {
    ClerkProvider: ({ children }: { children: React.ReactNode }) => <>{children}</>,
    useAuth: jest.fn(() => ({ isSignedIn: false })),
    SignedIn: (props: { children: React.ReactNode }) => <>{props.children}</>,
    UserButton: () => <div data-testid="user-button" />,
  };
});

function renderWithClerk(ui: React.ReactElement) {
  return render(ui);
}

describe('Navigation component', () => {
    it('render navigation links', () => {
        renderWithClerk(
            <MemoryRouter>
                <Routes>
                    <Route path="/" element={<Navigation />} />
                    <Route path="/login" element={<Navigation />} />
                    <Route path="/signup" element={<Navigation />} />
                </Routes>
            </MemoryRouter>
        );
    });
    it('shows login and signup buttons on home page', () => {
        renderWithClerk(
            <MemoryRouter initialEntries={["/"]}>
                <Routes>
                    <Route path="/" element={<Navigation />} />
                </Routes>
            </MemoryRouter>
        );
        expect(screen.getByText('Login')).toBeInTheDocument();
        expect(screen.getByText('Sign Up')).toBeInTheDocument();
    });
    it('shows go back home button on login page', () => {
        renderWithClerk(
            <MemoryRouter initialEntries={["/login"]}>
                <Routes>
                    <Route path="/login" element={<Navigation />} />
                </Routes>
            </MemoryRouter>
        );
        expect(screen.getByText('Go Back Home')).toBeInTheDocument();
    });
    it('renders signup link on login page', () => {
        renderWithClerk(
            <MemoryRouter initialEntries={["/login"]}>
                <Routes>
                    <Route path="/login" element={<Navigation />} />
                </Routes>
            </MemoryRouter>
        );
        expect(screen.queryByText('Sign Up')).toBeNull();
    });
});