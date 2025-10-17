import { Link } from 'react-router-dom'
import type { Event as EventType } from '../../types/types'

export const Event = ({ id, title, location, owner, time }: EventType) => {
    return (
        <Link 
            to={`/event/${id}`}
            style={{ textDecoration: 'none', color: 'inherit' }}
        >
            <div style={{
                padding: '1rem',
                backgroundColor: 'white',
                borderRadius: '4px',
                border: '1px solid #eee',
                cursor: 'pointer'
            }}>
                <h3 style={{ margin: '0 0 0.5rem 0' }}>{title}</h3>
                <div style={{ fontSize: '0.9rem', color: '#666' }}>
                    <div>📍 {location}</div>
                    <div>👤 {owner} • 🕐 {time}</div>
                </div>
            </div>
        </Link>
    )
}
