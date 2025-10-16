import React from 'react'
import type { Event as EventType } from '../../types/types'

export const Event = ({ title, location, owner, time }: EventType) => {
    return (
        <div style={{
            border: '1px solid #ddd',
            borderRadius: '8px',
            padding: '1rem',
            margin: '1rem 0',
            backgroundColor: '#f9f9f9'
        }}>
            <h3 style={{ margin: '0 0 0.5rem 0', color: '#333' }}>{title}</h3>
            <p style={{ margin: '0.25rem 0', color: '#666' }}>📍 {location}</p>
            <p style={{ margin: '0.25rem 0', color: '#666' }}>👤 {owner}</p>
            <p style={{ margin: '0.25rem 0', color: '#666' }}>🕐 {time}</p>
        </div>
    )
}
