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
  
  


  const handleSubmit = async(e: React.FormEvent) => {
    e.preventDefault();
    
    // Validate required fields
    if (!title || !description || !location || !time || !backendUser?.id) {
      alert("Please fill in all required fields and ensure you're logged in.");
      return;
    }

    try {
      const combinedDateTime = `${date}T${time}:00`;

      const response = await fetch(`${BASE_API_URL}/users/${backendUser.id}/events`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          title: title,
          description: description,
          isPublic: true,
          startTime: combinedDateTime,
          address: location,
        }),
      });

      if (response.ok) {
        const createdEvent = await response.json();
        console.log("Event created successfully:", createdEvent);
        alert("Event created successfully!");
        
        // Reset form
        setTitle('');
        setDescription('');
        setLocation('');
        setTime('');
        setImageUrl('');
      } else {
        const errorText = await response.text();
        console.error("Failed to create event:", response.status, errorText);
        alert(`Failed to create event: ${response.status} - ${errorText}`);
      }
    } catch (error) {
      console.error("Error creating event:", error);
      alert("An error occurred while creating the event. Please try again.");
    }
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
