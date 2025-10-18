import { useParams, useNavigate } from 'react-router-dom'
import { events } from '../types/types'
import './EventDetails.css'

export default function EventDetails() {
  const { id } = useParams<{ id: string }>()
  const navigate = useNavigate()
  
  const event = events.find(e => e.id === id)

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
              Join us for an unforgettable experience. This event brings together 
              community members for a memorable gathering.
            </p>
            
            <div className="event-info-details">
              <div className="event-detail-group">
                <div className="event-detail-label">Location</div>
                <div className="event-detail-item">{event.location}</div>
              </div>
              <div className="event-detail-group">
                <div className="event-detail-label">Host</div>
                <div className="event-detail-item">{event.owner}</div>
              </div>
              <div className="event-detail-group">
                <div className="event-detail-label">Time</div>
                <div className="event-detail-item">{event.time}</div>
              </div>
            </div>
          </div>
          
          <div className="event-image-container">
            <img 
              src="/path-to-your-event-image.jpg" 
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
          <button className="rsvp-button">
            RSVP Now
          </button>
        </div>
      </div>
    </div>
  )
}