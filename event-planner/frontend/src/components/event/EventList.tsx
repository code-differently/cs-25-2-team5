import { events } from '../../types/types'
import { Event } from './Event'
import './EventList.css'

const EventList = () => {
  return (
    <div className="event-list-container">
      <h2 className="event-list-title">
        Events
      </h2>

      <div className="event-list">
        {events.map((event) => (
          <Event key={event.id} {...event} />
        ))}
      
      {events.length === 0 && (
        <p style={{ textAlign: 'center', color: '#666' }}>
          No events available
        </p>
      )}
      </div>
    </div>
  )
}

export default EventList;