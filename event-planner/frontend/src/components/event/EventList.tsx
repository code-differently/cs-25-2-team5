import React from 'react'
import { events } from '../../types/types'
import { Event } from './Event'

const EventList = () => {
  return (
    <div style={{
      padding: '1rem',
      maxWidth: '800px',
      margin: '0 auto'
    }}>
      <h2 style={{ 
        marginBottom: '1.5rem', 
        color: '#333',
        textAlign: 'center' 
      }}>
        Upcoming Events
      </h2>
      
      <div style={{
        display: 'grid',
        gap: '1rem'
      }}>
        {events.map((event, index) => (
          <Event 
            key={index}
            title={event.title}
            location={event.location}
            owner={event.owner}
            time={event.time}
          />
        ))}
      </div>
      
      {events.length === 0 && (
        <p style={{ 
          textAlign: 'center', 
          color: '#666',
          fontStyle: 'italic' 
        }}>
          No events available at the moment.
        </p>
      )}
    </div>
  )
}

export default EventList