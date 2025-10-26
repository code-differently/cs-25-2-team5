import { useParams, useNavigate } from 'react-router-dom'

import './EventDetails.css'
import { useEffect } from 'react';
import React from 'react';

export default function EventDetails() {
  const { id } = useParams<{ id: string }>();
  const [event, setEvent] = React.useState<any>(null);
  const API_URL = import.meta.env.VITE_API_URL
  const navigate = useNavigate();
  useEffect(() => {
    const fetchEvent = async () => {
      const response = await fetch(`${API_URL}/events/${id}`)
      const data = await response.json();
      setEvent({data}.data)
    }

    fetchEvent()
  }, [id])

  console.log(event)
  if (!event) {
    return (
      <div className="event-not-found">
        <h2>Event Not Found</h2>
        <button className="not-found-button" onClick={() => navigate('/')}>
          Back to Events
        </button>
      </div>
    )
  }

  return (
    <div className="event-details-container">
      <div className="event-card">
        <div className="event-header">
          <div className="event-info-section">
            <h1 className="event-title">{event.title}</h1>
            <p className="event-description">
              {event.description}
            </p>
            
            <div className="event-info-details">
              <div className="event-detail-group">
                <div className="event-detail-label">Location</div>
                <div className="event-detail-item">{event.address}</div>
              </div>
              <div className="event-detail-group">
                {/* <div className="event-detail-label">Host</div>
                <div className="event-detail-item">{event.organizer.name}</div> */}
              </div>
              <div className="event-detail-group">
                <div className="event-detail-label">Time</div>
                <div className="event-detail-item">{event.startTime}</div>
              </div>
            </div>
          </div>
          
          <div className="event-image-container">
            <img 
              src={event.imageUrl || "/dinner-table-setup.jpg"} 
              alt={event.title} 
              className="event-image"
            />
          </div>
        </div>
        
        <p className="event-caption">
          Experience the perfect blend of community and celebration. 
          Mark your calendar and join us for this special occasion!
        </p>
        
        <div className="event-detail-button-group">
          <button className="back-button" onClick={() => navigate('/')}>
            ← Back
          </button>
        </div>
      </div>
    </div>
  )
}