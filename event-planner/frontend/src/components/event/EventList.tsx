import { events } from '../../types/types'
import { Event } from './Event'

const EventList = () => {
  return (
    <div style={{ padding: '2rem', maxWidth: '600px', margin: '0 auto' }}>
      <h2 style={{ marginBottom: '1.5rem', textAlign: 'center' }}>
        Events
      </h2>
      
      <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
        {events.map((event) => (
          <Event key={event.id} {...event} />
        ))}
      </div>
      
      {events.length === 0 && (
        <p style={{ textAlign: 'center', color: '#666' }}>
          No events available
        </p>
      )}
    </div>
  )
}

export default EventList;