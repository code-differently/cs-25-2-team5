import React from 'react'
import { useNavigate } from 'react-router-dom'
import type { Event as EventType } from '../../types/types'

export const Event = ({ id, title, location, owner, time }: EventType) => {
    const navigate = useNavigate()
    
    const handleClick = () => {
        navigate(`/event/${id}`)
    }

    return (
        <div 
            onClick={handleClick}
            style={{
                border: '1px solid #ddd',
                borderRadius: '8px',
                padding: '1rem',
                margin: '1rem 0',
                backgroundColor: '#f9f9f9',
                cursor: 'pointer',
                transition: 'all 0.2s ease'
            }}
            onMouseEnter={(e) => {
                e.currentTarget.style.backgroundColor = '#f0f0f0'
                e.currentTarget.style.transform = 'translateY(-2px)'
                e.currentTarget.style.boxShadow = '0 4px 8px rgba(0,0,0,0.1)'
            }}
            onMouseLeave={(e) => {
                e.currentTarget.style.backgroundColor = '#f9f9f9'
                e.currentTarget.style.transform = 'translateY(0)'
                e.currentTarget.style.boxShadow = 'none'
            }}
        >
            <h3 style={{ margin: '0 0 0.5rem 0', color: '#333' }}>{title}</h3>
            <p style={{ margin: '0.25rem 0', color: '#666' }}>📍 {location}</p>
            <p style={{ margin: '0.25rem 0', color: '#666' }}>👤 {owner}</p>
            <p style={{ margin: '0.25rem 0', color: '#666' }}>🕐 {time}</p>
        </div>
    )
}
