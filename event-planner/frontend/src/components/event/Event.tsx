import { Link } from 'react-router-dom'
import type { Event as EventType } from '../../types/types'
import './Event.css'

export const Event = ({id, title, location, organizer, description, startTime, imageUrl} : any) => {
  return (
    
    <Link to={`/event/${id}`} className="event-list-card-link">
      <div className="event-list-card">
        <div className="event-list-card-image-wrapper">
          <img 
            src={imageUrl || '/default-event-image.jpg'} 
            alt={title}
            className="event-list-card-image"
          />
        </div>
        <div className="event-list-card-body">
          <h3 className="event-list-card-title">{title}</h3>
          <div className="event-list-card-details">
            <div className="event-list-card-detail-row">{location}</div>
            <div className="event-list-card-detail-row">{} • {startTime}</div>
            <div className="event-list-card-detail-row">{description}</div>
          </div>
        </div>
      </div>
    </Link>
  )
}