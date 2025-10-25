import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import Invitations from './Invitations';
import '@testing-library/jest-dom';
import { type PrivateEventDetails, invitedEvents } from '../../types/types';

describe('Invitations Page', () => {
    it('renders the invitations list', () => {
        render(<Invitations />);
        const header = screen.getByText('Event Invitations');
        expect(header).toBeInTheDocument();
    });
    it('displays invited events', () => {
        render(<Invitations />);
        invitedEvents.forEach((event: PrivateEventDetails) => {
            const eventButton = screen.getByText(event.title);
            expect(eventButton).toBeInTheDocument();
        });
    });
    it('displays event buttons for each invited event', () => {
        render(<Invitations />);
        invitedEvents.forEach((event: PrivateEventDetails) => {
            const eventButton = screen.getByRole('button', { name: event.title });
            expect(eventButton).toBeInTheDocument();
        });
    });

    it('shows event details when an event button is clicked', async () => {
        render(<Invitations />);
        const eventButton = screen.getByRole('button', { name: invitedEvents[0].title });
        await userEvent.click(eventButton);
        const eventDetails = screen.getByText(invitedEvents[0].description);
        expect(eventDetails).toBeInTheDocument();
    });
    it('updates RSVP status when Accept button is clicked', async () => {
        render(<Invitations />);
        const eventButton = screen.getByRole('button', { name: invitedEvents[0].title });
        await userEvent.click(eventButton);
        const acceptButton = screen.getByRole('button', { name: 'Accept' });
        await userEvent.click(acceptButton);
        // Use data-testid for easier and more robust selection
        const rsvpStatus = await screen.findByTestId('rsvp-status');
        expect(rsvpStatus).toHaveTextContent('RSVP Status: Accepted');
    });

    it('updates RSVP status when Decline button is clicked', async () => {
        render(<Invitations />);
        const eventButton = screen.getByRole('button', { name: invitedEvents[0].title });
        await userEvent.click(eventButton);
        const declineButton = screen.getByRole('button', { name: 'Decline' });
        await userEvent.click(declineButton);
        const rsvpStatus = await screen.findByTestId('rsvp-status');
        expect(rsvpStatus).toHaveTextContent('RSVP Status: Declined');
    });
    it('disables Accept button when RSVP status is accepted', async () => {
        render(<Invitations />);
        const eventButton = screen.getByRole('button', { name: invitedEvents[0].title });
        await userEvent.click(eventButton);
        const acceptButton = screen.getByRole('button', { name: 'Accept' });
        await userEvent.click(acceptButton);
        expect(acceptButton).toBeDisabled();
    });
    it('disables Decline button when RSVP status is declined', async () => {
        render(<Invitations />);
        const eventButton = screen.getByRole('button', { name: invitedEvents[0].title });
        await userEvent.click(eventButton);
        const declineButton = screen.getByRole('button', { name: 'Decline' });
        await userEvent.click(declineButton);
        expect(declineButton).toBeDisabled();
    }); 
    it('enables both buttons when RSVP status is pending', async () => {
        render(<Invitations />);
        const eventButton = screen.getByRole('button', { name: invitedEvents[0].title });
        await userEvent.click(eventButton);
        const acceptButton = screen.getByRole('button', { name: 'Accept' });
        const declineButton = screen.getByRole('button', { name: 'Decline' });
        expect(acceptButton).toBeEnabled();
        expect(declineButton).toBeEnabled();
    });
    it('shows no event details when no event is selected', () => {
        render(<Invitations />);
        const eventDetails = screen.queryByText(/Date:/);
        expect(eventDetails).not.toBeInTheDocument();
    });
    it('shows correct event details for selected event', async () => {
        render(<Invitations />);
        const eventButton = screen.getByRole('button', { name: invitedEvents[1].title });
        await userEvent.click(eventButton);
        const eventDetails = screen.getByText(invitedEvents[1].description);
        expect(eventDetails).toBeInTheDocument();
    });
});