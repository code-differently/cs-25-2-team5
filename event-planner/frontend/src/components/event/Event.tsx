import { Link } from 'react-router-dom'
import type { Event as EventType } from '../../types/types'
import './Event.css'

export const Event = ({ id, title, location, owner, time }: EventType) => {
    return (
        <Link 
            to={`/event/${id}`}
            className="event-list-card-link"
        >
            <div className="event-list-card">
                <h3 className="event-list-card-title">{title}</h3>
                <div className="event-list-card-details">
                    <div className="event-list-card-detail-row">📍 {location}</div>
                    <div className="event-list-card-detail-row">👤 {owner} • 🕐 {time}</div>
                </div>
            </div>
        </Link>
    )
}
