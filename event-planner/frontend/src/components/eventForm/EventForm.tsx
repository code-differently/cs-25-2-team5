import React from "react";
import { useState } from "react";
import './EventForm.css';

const EventForm: React.FC = () => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [location, setLocation] = useState('');
    const [owner, setOwner] = useState('');
    const [time, setTime] = useState('');
    const [imageUrl, setImageUrl] = useState('');

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        const eventData = {
            title,
            description,
            location,
            owner,
            time,
            imageUrl
        };
        console.log('Event created: ', eventData);
    };

    return (
        <div className="event-form-section">
            <div className="event-form-container">
            <h1 className="event-form-header">Create an Event</h1>
    
            <form className="event-form" onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="event-title">Title</label>
                    <input id="event-title" type="text" className="event-form-input" value={title} onChange={e => setTitle(e.target.value)} placeholder="Event Title" required />
                </div>
                <div className="form-group">
                    <label htmlFor="event-description">Description</label>
                    <textarea id="event-description" className="event-form-input" value={description} onChange={e => setDescription(e.target.value)} placeholder="Event Description" required />
                </div>
                <div className="form-group">
                    <label htmlFor="event-location">Location</label>
                    <input id="event-location" type="text" className="event-form-input" value={location} onChange={e => setLocation(e.target.value)} placeholder="Event Location" required />
                </div>
                <div className="form-group">
                    <label htmlFor="event-owner">Owner</label>
                    <input id="event-owner" type="text" className="event-form-input" value={owner} onChange={e => setOwner(e.target.value)} placeholder="Event Owner" required />
                </div>
                <div className="form-group">
                    <label htmlFor="event-time">Time</label>
                    <input id="event-time" type="datetime-local" className="event-form-input" value={time} onChange={e => setTime(e.target.value)} required />
                </div>
                <div className="form-group">
                    <label htmlFor="event-image-url">Image URL</label>
                    <input id="event-image-url" type="text" className="event-form-input" value={imageUrl} onChange={e => setImageUrl(e.target.value)} placeholder="Event Image URL" required />
                </div>
                <div className="form-group">
                    <label htmlFor="visibility">Visibility</label>
                    <select id="visibility" name="visibility" className="event-form-select">
                        <option value="public">Public</option>
                        <option value="private">Private</option>
                    </select>
                </div>
                <button type="submit" className="event-form-button">Create Event</button>
            </form>
            </div>
        </div>
    );
};

export default EventForm;