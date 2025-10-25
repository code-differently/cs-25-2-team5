import { render, screen } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import '@testing-library/jest-dom';
import Navigation from './Navigation';

describe('Navigation component', () => {
    it('render navigation links', () => {
        render(
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
        render(
            <MemoryRouter initialEntries={['/']}>
                <Routes>
                    <Route path="/" element={<Navigation />} />
                </Routes>
            </MemoryRouter>
        );
        expect(screen.getByText('Login')).toBeInTheDocument();
        expect(screen.getByText('Sign Up')).toBeInTheDocument();
    });
    it('shows go back home button on login page', () => {
        render(
            <MemoryRouter initialEntries={['/login']}>
                <Routes>
                    <Route path="/login" element={<Navigation />} />
                </Routes>
            </MemoryRouter>
        );
        expect(screen.getByText('Go Back Home')).toBeInTheDocument();
    });
    it('renders signup link on login page', () => {
        render(
            <MemoryRouter initialEntries={['/login']}>
                <Routes>
                    <Route path="/login" element={<Navigation />} />
                </Routes>
            </MemoryRouter>
        );
        expect(screen.queryByText('Sign Up')).toBeNull();
    });
});