import React from "react";
import { useState } from "react";
import './EventForm.css';
import EventFormFields from "./EventFormFields";
import GuestInput from "./GuestInput";
import GuestList from "./GuestList";

const EventForm: React.FC = () => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [location, setLocation] = useState('');
    const [organizer, setOrganizer] = useState('');
    const [time, setTime] = useState('');
    const [imageUrl, setImageUrl] = useState('');
    const [guests, setGuests] = useState<string[]>([]);
    const [guestInput, setGuestInput] = useState('');
    const [guestError, setGuestError] = useState<string | null>(null);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [visibility, setVisibility] = useState<'public' | 'private'>('public');

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setIsSubmitting(true);

        const eventData = {
            title,
            description,
            location,
            organizer, // Will be replaced with Clerk user ID/name when integrated
            time,
            imageUrl,
            guests: visibility === 'private' ? guests : [],
            visibility
        };

        try {
            // Send POST request to backend API
            const response = await fetch('/api/events', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    // When Clerk is integrated, add:
                    // 'Authorization': `Bearer ${await getToken()}`
                },
                body: JSON.stringify(eventData)
            });

            if (!response.ok) {
                const errorData = await response.json().catch(() => null);
                throw new Error(errorData?.message || `Failed to create event (${response.status})`);
            }

            const createdEvent = await response.json();
            console.log('Event created successfully:', createdEvent);
            
            alert('Event created successfully!');
            
            // Reset form after successful submission
            setTitle('');
            setDescription('');
            setLocation('');
            setOrganizer('');
            setTime('');
            setImageUrl('');
            setGuests([]);
            
            // Optional: Redirect to event page or events list
            // window.location.href = `/events/${createdEvent.id}`;
            // Or use React Router: navigate(`/events/${createdEvent.id}`);
            
        } catch (error) {
            console.error('Error creating event:', error);
            
            if (error instanceof Error) {
                alert('Error creating event: ' + error.message);
            } else {
                alert('Error creating event. Please try again.');
            }
        } finally {
            setIsSubmitting(false);
        }
    };

    // Adds a guest email to the guests list
    const handleAddGuest = () => {
        const trimmedEmail = guestInput.trim();
        if (trimmedEmail === '') {
            setGuestError(null);
            return;
        }
        // Basic email validation
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(trimmedEmail)) {
            setGuestError('Please enter a valid email address');
            return;
        }
        // Check for duplicate emails
        if (guests.includes(trimmedEmail)) {
            setGuestError('This guest has already been added');
            return;
        }
        setGuests([...guests, trimmedEmail]);
        setGuestInput('');
        setGuestError(null);
    };

    // Removes a guest from the guests list by index
    const handleRemoveGuest = (indexToRemove: number) => {
        setGuests(guests.filter((_, index) => index !== indexToRemove));
    };

    // Handles Enter key press in guest input to add guest
    const handleKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Enter') {
            e.preventDefault();
            handleAddGuest();
        }
    };

    return (
        <div className="event-form-section">
            <form className="event-form" onSubmit={handleSubmit}>
                <div className="event-visibility">
                <label>
                    Visibility:
                    <select
                        value={visibility}
                        onChange={e => setVisibility(e.target.value as 'public' | 'private')}
                        disabled={isSubmitting}
                    >
                        <option value="public">Public</option>
                        <option value="private">Private</option>
                    </select>
                </label>
                </div>
                {/* Main event fields */}
                <EventFormFields
                    title={title}
                    setTitle={setTitle}
                    description={description}
                    setDescription={setDescription}
                    location={location}
                    setLocation={setLocation}
                    organizer={organizer}
                    setOrganizer={setOrganizer}
                    time={time}
                    setTime={setTime}
                    imageUrl={imageUrl}
                    setImageUrl={setImageUrl}
                    isSubmitting={isSubmitting}
                />
                {/* Guest input/list only for private events */}
                {visibility === 'private' && (
                    <>
                        <GuestInput
                            guestInput={guestInput}
                            setGuestInput={value => {
                                setGuestInput(value);
                                if (guestError) setGuestError(null);
                            }}
                            handleAddGuest={handleAddGuest}
                            handleKeyPress={handleKeyPress}
                            isSubmitting={isSubmitting}
                            guestError={guestError}
                        />
                        <GuestList
                            guests={guests}
                            handleRemoveGuest={handleRemoveGuest}
                            isSubmitting={isSubmitting}
                        />
                    </>
                )}
                <button 
                    type="submit" 
                    className="event-form-button"
                    disabled={isSubmitting}
                >
                    {isSubmitting ? 'Creating Event...' : 'Create Event'}
                </button>
            </form>
        </div>
    );
};

export default EventForm;