import React, { useEffect, useState } from 'react';
import './EventForm.css';
import EventField from './EventField';
import { useUser } from '@clerk/clerk-react';
type User = {
  id: number;
  name: string;
  email: string; 
};
const EventForm: React.FC = () => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [location, setLocation] = useState('');
  const [date, setDate] = useState(''); // Separate date field
  const [time, setTime] = useState(''); 
  const [imageUrl, setImageUrl] = useState('');
  const [visibility, setVisibility] = useState('public');
  const {isSignedIn,user} = useUser();
  const [backendUser,setBackendUser] = useState<User>();
  const [guestEmails, setGuestEmails] = useState<string[]>([""]);

  if(!isSignedIn) {
    alert("You must be signed in to create an event.");
    window.location.href = "/login";
  }
  const BASE_API_URL = import.meta.env.VITE_API_URL
  const clerkId = user?.id
  useEffect(()=> {
    const fetchUser = async()=> {
    try {
        const response = await fetch(`${BASE_API_URL}/users/clerk/${clerkId}`)
        if (response.ok) {
            const json = await response.json();
            setBackendUser(json)
            
            
        } else {
            console.error("Failed to fetch user", response.status);
        }
        } catch (err) {
            console.error("Error fetching user:", err);
        }

  }
  fetchUser();

  },[clerkId])

  const handleGuestEmailChange = (index: number, value: string) => {
  const updated = [...guestEmails];
  updated[index] = value;
  setGuestEmails(updated);
};

const addGuestField = () => setGuestEmails([...guestEmails, ""]);

const removeGuestField = (index: number) => {
  setGuestEmails(guestEmails.filter((_, i) => i !== index));
};

  
  

  const validateInputs = () => {
    if (!title || !description || !location || !time || !date || !backendUser) {
        alert("Please fill in all required fields");
        return false;
    }
    return true;
  }
  const convertToDateTimeString = (date: string, time: string): string => {
    return `${date}T${time}:00`;
  }
  const createEvent = async (isPublic: boolean) => {
    if (!validateInputs() || !backendUser) return;

    const combinedDateTime = convertToDateTimeString(date, time);
    const endpoint = isPublic
      ? `${BASE_API_URL}/users/${backendUser.id}/events`
      : `${BASE_API_URL}/event-guests/organizer/${backendUser.id}/event/create`;

    const payload: Record<string, any> = {
      title,
      description,
      isPublic,
      startTime: combinedDateTime,
      address: location,
    };

    // Only include guestEmails for private events
    if (!isPublic) {
      payload.guestEmails = guestEmails;
    }
    console.log("Payload:", payload);

    try {
      const response = await fetch(endpoint, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
      });

      if (response.ok) {
        await response.json();

        alert('Event created successfully!');
        resetForm();
      } else {
        const errorText = await response.text();
        console.error('Failed to create event:', response.status, errorText);
        alert(`Failed to create event: ${response.status}`);
      }
    } catch (error) {
      console.error('Error creating event:', error);
      alert('An error occurred while creating the event. Please try again.');
    }
  };

  const resetForm = () => {
    setTitle('');
    setDescription('');
    setLocation('');
    setDate('');
    setTime('');
    setImageUrl('');
    setGuestEmails(['']);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    createEvent(visibility === 'public');
  };


  return (
    <div className="event-form-section">
      <div className="event-form-container">
        <h1 className="event-form-header">Create an Event</h1>

        <form className="event-form" onSubmit={handleSubmit}>
          <EventField
            id="event-title"
            label="Title"
            value={title}
            onChange={e => setTitle(e.target.value)}
            placeholder="Event Title"
            required
          />

          <EventField
            id="event-description"
            label="Description"
            value={description}
            onChange={e => setDescription(e.target.value)}
            placeholder="Event Description"
            textarea
            required
          />

          <EventField
            id="event-location"
            label="Location"
            value={location}
            onChange={e => setLocation(e.target.value)}
            placeholder="Event Location"
            required
          />

          <EventField
            id="event-date"
            label="Date"
            type="date"
            value={date}
            onChange={e => setDate(e.target.value)}
            required
          />


          <EventField
            id="event-time"
            label="Time"
            type="time"
            value={time}
            onChange={e => setTime(e.target.value)}
            required
          />

          <EventField
            id="event-image-url"
            label="Image URL"
            value={imageUrl}
            onChange={e => setImageUrl(e.target.value)}
            placeholder="Event Image URL"
            required
          />

          <div className="form-group">
            <label htmlFor="visibility">Visibility</label>
            <select
              id="visibility"
              name="visibility"
              className="event-form-select"
              value={visibility}
              onChange={e => setVisibility(e.target.value)}
            >
              <option value="public">Public</option>
              <option value="private">Private</option>
            </select>
            {visibility === "private" && (
  <div className="form-group">
    <label htmlFor="guestEmails">Invite Guests (Emails)</label>

    {
    guestEmails.map((email, index) => (
      <div key={index} className="guest-email-row">
        <input
          type="email"
          className="event-form-input"
          placeholder="guest@example.com"
          value={email}
          onChange={(e) => handleGuestEmailChange(index, e.target.value)}
        />
        {guestEmails.length > 1 && (
          <button
            type="button"
            className="remove-guest-btn"
            onClick={() => removeGuestField(index)}
          >
            ✕
          </button>
        )}
      </div>
    ))}

    <button
      type="button"
      className="add-guest-btn"
      onClick={addGuestField}
    >
      + Add another guest
    </button>
  </div>
)}

          </div>

          <button type="submit" className="event-form-button">
            Create Event
          </button>
        </form>
      </div>
    </div>
  );
};

export default EventForm;
