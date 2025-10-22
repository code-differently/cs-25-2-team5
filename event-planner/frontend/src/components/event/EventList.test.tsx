import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import { Event } from './Event';

// Define a mock event object with the required props for Event component
const mockEvent = {
  id: '1',
  title: 'Sample Event',
  date: '2024-06-01',
  location: 'Sample Location',
  description: 'Sample Description',
  owner: 'Sample Owner',
  time: '12:00',
  // Add other required props here as needed by the Event component
};

const mockEventList = (
  <>
    <Event {...mockEvent} />
    <Event {...mockEvent} />
    <Event {...mockEvent} />
  </>
);

describe('EventList component', () => {
  it('renders a list of events', () => {
    render(
      <MemoryRouter>
        {mockEventList}
      </MemoryRouter>
    );
    expect(screen.getAllByRole('link')).toHaveLength(3);
  });
});

