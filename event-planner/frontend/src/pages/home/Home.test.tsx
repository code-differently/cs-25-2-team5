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
        expect(screen.getByText('Welcome to Evynt')).toBeInTheDocument();
        expect(screen.getByText('Discover and create amazing events in your community!')).toBeInTheDocument();
    });

    it ('renders EventList component', () => {
        render(
            <MemoryRouter>
                <EventList />
            </MemoryRouter>
    );
    expect(screen.getByText('Events')).toBeInTheDocument();
    });
    
    {/* Additional tests will be added after Authentication is implemented */}
});