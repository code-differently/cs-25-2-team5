import { useParams, useNavigate } from 'react-router-dom'
import { events } from '../types/types'

export default function EventDetails() {
  const { id } = useParams<{ id: string }>()
  const navigate = useNavigate()
  
  const event = events.find(e => e.id === id)
  
  if (!event) {
    return (
      <div style={{
        padding: '2rem',
        textAlign: 'center',
        maxWidth: '800px',
        margin: '0 auto'
      }}>
        <h2 style={{ color: '#e74c3c', marginBottom: '1rem' }}>Event Not Found</h2>
        <p style={{ color: '#666', marginBottom: '2rem' }}>
          The event you're looking for doesn't exist or has been removed.
        </p>
        <button 
          onClick={() => navigate('/')}
          style={{
            backgroundColor: '#3498db',
            color: 'white',
            border: 'none',
            padding: '0.75rem 1.5rem',
            borderRadius: '5px',
            cursor: 'pointer',
            fontSize: '1rem'
          }}
        >
          Back to Events
        </button>
      </div>
    )
  }

  return (
    <div style={{
      padding: '2rem',
      maxWidth: '800px',
      margin: '0 auto'
    }}>
      <button 
        onClick={() => navigate('/')}
        style={{
          backgroundColor: '#95a5a6',
          color: 'white',
          border: 'none',
          padding: '0.5rem 1rem',
          borderRadius: '5px',
          cursor: 'pointer',
          marginBottom: '2rem',
          fontSize: '0.9rem'
        }}
      >
        ← Back to Events
      </button>
      
      <div style={{
        backgroundColor: 'white',
        borderRadius: '12px',
        padding: '2rem',
        boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
        border: '1px solid #e1e8ed'
      }}>
        <h1 style={{ 
          color: '#2c3e50', 
          marginBottom: '1.5rem',
          fontSize: '2.5rem',
          fontWeight: 'bold'
        }}>
          {event.title}
        </h1>
        
        <div style={{
          display: 'grid',
          gap: '1.5rem',
          gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))'
        }}>
          <div style={{
            padding: '1.5rem',
            backgroundColor: '#f8f9fa',
            borderRadius: '8px',
            border: '1px solid #e9ecef'
          }}>
            <h3 style={{ 
              color: '#495057', 
              marginBottom: '1rem',
              fontSize: '1.2rem',
              display: 'flex',
              alignItems: 'center',
              gap: '0.5rem'
            }}>
              📍 Location
            </h3>
            <p style={{ 
              color: '#6c757d', 
              fontSize: '1.1rem',
              margin: 0 
            }}>
              {event.location}
            </p>
          </div>
          
          <div style={{
            padding: '1.5rem',
            backgroundColor: '#f8f9fa',
            borderRadius: '8px',
            border: '1px solid #e9ecef'
          }}>
            <h3 style={{ 
              color: '#495057', 
              marginBottom: '1rem',
              fontSize: '1.2rem',
              display: 'flex',
              alignItems: 'center',
              gap: '0.5rem'
            }}>
              👤 Event Organizer
            </h3>
            <p style={{ 
              color: '#6c757d', 
              fontSize: '1.1rem',
              margin: 0 
            }}>
              {event.owner}
            </p>
          </div>
          
          <div style={{
            padding: '1.5rem',
            backgroundColor: '#f8f9fa',
            borderRadius: '8px',
            border: '1px solid #e9ecef'
          }}>
            <h3 style={{ 
              color: '#495057', 
              marginBottom: '1rem',
              fontSize: '1.2rem',
              display: 'flex',
              alignItems: 'center',
              gap: '0.5rem'
            }}>
              🕐 Time
            </h3>
            <p style={{ 
              color: '#6c757d', 
              fontSize: '1.1rem',
              margin: 0 
            }}>
              {event.time}
            </p>
          </div>
        </div>
        
        <div style={{
          marginTop: '2rem',
          padding: '1.5rem',
          backgroundColor: '#e8f5e8',
          borderRadius: '8px',
          border: '1px solid #c3e6c3'
        }}>
          <h3 style={{ 
            color: '#2d5a2d', 
            marginBottom: '1rem',
            fontSize: '1.2rem'
          }}>
            📝 Event Description
          </h3>
          <p style={{ 
            color: '#4a6741', 
            fontSize: '1rem',
            lineHeight: '1.6',
            margin: 0 
          }}>
            Welcome to {event.title}! Join us at {event.location} for an amazing experience. 
            This event is organized by {event.owner} and will take place at {event.time}. 
            Don't miss out on this incredible opportunity to connect and engage with fellow participants.
          </p>
        </div>
        
        <div style={{
          marginTop: '2rem',
          display: 'flex',
          gap: '1rem',
          flexWrap: 'wrap'
        }}>
          <button style={{
            backgroundColor: '#27ae60',
            color: 'white',
            border: 'none',
            padding: '0.75rem 2rem',
            borderRadius: '6px',
            cursor: 'pointer',
            fontSize: '1rem',
            fontWeight: 'bold'
          }}>
            RSVP to Event
          </button>
          
          <button style={{
            backgroundColor: '#3498db',
            color: 'white',
            border: 'none',
            padding: '0.75rem 2rem',
            borderRadius: '6px',
            cursor: 'pointer',
            fontSize: '1rem'
          }}>
            Share Event
          </button>
          
          <button style={{
            backgroundColor: '#f39c12',
            color: 'white',
            border: 'none',
            padding: '0.75rem 2rem',
            borderRadius: '6px',
            cursor: 'pointer',
            fontSize: '1rem'
          }}>
            Add to Calendar
          </button>
        </div>
      </div>
    </div>
  );
}

