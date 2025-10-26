import React, { useState } from 'react';
import './EventForm.css';
import EventField from './EventField';
import { useUser } from '@clerk/clerk-react';
const EventForm: React.FC = () => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [location, setLocation] = useState('');
  const [owner, setOwner] = useState('');
  const [time, setTime] = useState('');
  const [imageUrl, setImageUrl] = useState('');
  const [visibility, setVisibility] = useState('public');
  const {isSignedIn,user} = useUser();

  if(!isSignedIn) {
    alert("You must be signed in to create an event.");
    window.location.href = "/login";
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const eventData = { title, description, location, owner, time, imageUrl, visibility };
    console.log('Event created:', eventData);
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
            id="event-owner"
            label="Owner"
            value={owner}
            onChange={e => setOwner(e.target.value)}
            placeholder="Event Owner"
            required
          />

          <EventField
            id="event-time"
            label="Time"
            type="datetime-local"
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
