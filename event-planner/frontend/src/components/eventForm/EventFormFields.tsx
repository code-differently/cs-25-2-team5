import React from "react";

interface EventFormFieldsProps {
  title: string;
  setTitle: (v: string) => void;
  description: string;
  setDescription: (v: string) => void;
  location: string;
  setLocation: (v: string) => void;
  owner: string;
  setOwner: (v: string) => void;
  time: string;
  setTime: (v: string) => void;
  imageUrl: string;
  setImageUrl: (v: string) => void;
  isSubmitting: boolean;
}

const EventFormFields: React.FC<EventFormFieldsProps> = ({
  title,
  setTitle,
  description,
  setDescription,
  location,
  setLocation,
  owner,
  setOwner,
  time,
  setTime,
  imageUrl,
  setImageUrl,
  isSubmitting
}) => (
  <>
    <div className="form-group">
      <label htmlFor="event-title">Title</label>
      <input
        id="event-title"
        type="text"
        className="event-form-input"
        value={title}
        onChange={e => setTitle(e.target.value)}
        placeholder="Event Title"
        required
        disabled={isSubmitting}
      />
    </div>
    <div className="form-group">
      <label htmlFor="event-description">Description</label>
      <textarea
        id="event-description"
        className="event-form-input"
        value={description}
        onChange={e => setDescription(e.target.value)}
        placeholder="Event Description"
        required
        disabled={isSubmitting}
      />
    </div>
    <div className="form-group">
      <label htmlFor="event-location">Location</label>
      <input
        id="event-location"
        type="text"
        className="event-form-input"
        value={location}
        onChange={e => setLocation(e.target.value)}
        placeholder="Event Location"
        required
        disabled={isSubmitting}
      />
    </div>
    {/* Owner Input (will be replaced by Clerk user info) */}
    <div className="form-group">
      <label htmlFor="event-owner">Owner</label>
      <input
        id="event-owner"
        type="text"
        className="event-form-input"
        value={owner}
        onChange={e => setOwner(e.target.value)}
        placeholder="Event Owner"
        required
        disabled={isSubmitting}
      />
    </div>
    <div className="form-group">
      <label htmlFor="event-time">Time</label>
      <input
        id="event-time"
        type="datetime-local"
        className="event-form-input"
        value={time}
        onChange={e => setTime(e.target.value)}
        required
        disabled={isSubmitting}
      />
    </div>
    <div className="form-group">
      <label htmlFor="event-image-url">Image URL</label>
      <input
        id="event-image-url"
        type="url"
        className="event-form-input"
        value={imageUrl}
        onChange={e => setImageUrl(e.target.value)}
        placeholder="https://example.com/image.jpg"
        required
        disabled={isSubmitting}
      />
    </div>
    <div className="form-group">
      <label htmlFor="visibility">Visibility</label>
      <select
        id="visibility"
        name="visibility"
        className="event-form-select"
        disabled={isSubmitting}
      >
        <option value="public">Public</option>
      </select>
    </div>
  </>
);

export default EventFormFields;
