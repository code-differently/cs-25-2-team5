import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import CreateEvent from './CreateEvent';
import EventForm from '../../components/eventForm/EventForm';

describe('Create Event Page', () => {
    it('renders Create Event page with form and image', () => {
        render(
            <MemoryRouter>
                <CreateEvent />
            </MemoryRouter>
        );
        expect(screen.getByRole('heading', { name: /Create an Event/i, level: 2 })).toBeInTheDocument();
        expect(screen.getByRole('img', { name: /Event Hero/i })).toBeInTheDocument();
        expect(screen.getByText(/Plan your next gathering with ease. Fill out the form to get started!/i)).toBeInTheDocument();
    });
    it('renders EventForm component', () => {
        const { container } = render(
            <MemoryRouter>
                <CreateEvent />
                <EventForm />
            </MemoryRouter>
        );
        expect(container.querySelector('form')).toBeInTheDocument();
    });
});