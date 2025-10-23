import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import '@testing-library/jest-dom'; 
import Home from './Home';
import EventList from '../../components/event/EventList';

describe('Home Page', () => {
    it('render welcome message and description', () => {
        render(
            <MemoryRouter>
                <Home />
            </MemoryRouter>
        );
        expect(screen.queryByText('Welcome to Evynt')).toBeNull();
        expect(screen.queryByText('Discover and create amazing events in your community!')).toBeInTheDocument();
    });

    it ('renders EventList component', () => {
        render(
            <MemoryRouter>
                <EventList />
            </MemoryRouter>
    );
    expect(screen.getByText('Events')).toBeInTheDocument();
    });
});