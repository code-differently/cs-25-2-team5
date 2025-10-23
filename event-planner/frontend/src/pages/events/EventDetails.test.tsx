import { render, screen } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import '@testing-library/jest-dom';
import EventDetails from './EventDetails';
import * as typesModule from '../../types/types';

const mockEvents = [
	{
		id: '1',
		title: 'Sample Event',
		location: 'Sample Location',
		owner: 'Sample Owner',
		time: '2025-12-15T18:00:00Z',
		description: 'Sample Description',
		imageUrl: '/sample.jpg',
	},
];

describe('EventDetails component', () => {
	beforeEach(() => {
        typesModule.events.length = 0;
        typesModule.events.push(...mockEvents);
    });

	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('renders details for a valid event', () => {
		render(
			<MemoryRouter initialEntries={['/event/1']}>
				<Routes>
					<Route path="/event/:id" element={<EventDetails />} />
				</Routes>
			</MemoryRouter>
		);
		expect(screen.getByText('Sample Event')).toBeInTheDocument();
		expect(screen.getByText('Sample Location')).toBeInTheDocument();
		expect(screen.getByText('Sample Owner')).toBeInTheDocument();
		expect(screen.getByText('2025-12-15T18:00:00Z')).toBeInTheDocument();
		expect(screen.getByText('Sample Description')).toBeInTheDocument();
		expect(screen.getByRole('img')).toHaveAttribute('src', '/sample.jpg');
		expect(screen.getByRole('button', { name: /Back/i })).toBeInTheDocument();
	});

	it('shows Event Not Found for invalid event id', () => {
		render(
			<MemoryRouter initialEntries={['/event/999']}>
				<Routes>
					<Route path="/event/:id" element={<EventDetails />} />
				</Routes>
			</MemoryRouter>
		);
		expect(screen.getByText('Event Not Found')).toBeInTheDocument();
		expect(screen.getByRole('button', { name: /Back to Events/i })).toBeInTheDocument();
	});
    it('renders the back button', () => {
        render(
            <MemoryRouter initialEntries={['/event/1']}>
                <Routes>
                    <Route path="/event/:id" element={<EventDetails />} />
                </Routes>
            </MemoryRouter>
        );
        expect(screen.getByRole('button', { name: /Back/i })).toBeInTheDocument();
    });
    it('uses default image when imageUrl is not provided', () => {
        const eventsWithoutImage = [
            {
                id: '2',
                title: 'Event Without Image',
                location: 'Location 2',
                owner: 'Owner 2',
                time: '2025-11-20T15:00:00Z',
                description: 'Description 2',
                imageUrl: '',
            },
        ];
        typesModule.events.length = 0;
        typesModule.events.push(...eventsWithoutImage);

        render(
            <MemoryRouter initialEntries={['/event/2']}>
                <Routes>
                    <Route path="/event/:id" element={<EventDetails />} />
                </Routes>
            </MemoryRouter>
        );
        expect(screen.getByRole('img')).toHaveAttribute('src', '/dinner-table-setup.jpg');
    });
});