import { Event } from './Event'
import { useState,useEffect } from 'react'
import './EventList.css'
import { useUser } from '@clerk/clerk-react'

const EventList = () => {
  const [events, setEvents] = useState<any[]>([])
  const API_URL = import.meta.env.VITE_API_URL
  const { isSignedIn, isLoaded, user } = useUser();
  useEffect(() => {
    const fetchEvents = async () => {
      const response = await fetch(`${API_URL}/events/community`)
      const data = await response.json()
      console.log(data);
      setEvents(data)
    }

    fetchEvents()
  }, [])
  console.log(user);
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