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
        <h1 className="event-title">
          {event.title}
        </h1>
        
        <div className="event-details">
          <div className="event-detail-item">
            <span>📍</span>
            <span>{event.location}</span>
          </div>
          <div className="event-detail-item">
            <span>👤</span>
            <span>{event.owner}</span>
          </div>
          <div className="event-detail-item">
            <span>🕐</span>
            <span>{event.time}</span>
          </div>
        </div>
        <div className='event-detail-button-group'>
          <button className="back-button" onClick={() => navigate('/')}>
            ← Back
          </button>
          <button className="rsvp-button">
            RSVP
          </button>
        </div>
      </div>
    </div>
  )
}

