import React from 'react';

interface EventFieldProps {
  id: string;
  label: string;
  type?: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => void;
  placeholder?: string;
  textarea?: boolean;
  required?: boolean;
}

const EventField: React.FC<EventFieldProps> = ({
  id,
  label,
  type = 'text',
  value,
  onChange,
  placeholder,
  textarea = false,
  required = false
}) => {
  return (
    <div className="form-group">
      <label htmlFor={id}>{label}</label>
      {textarea ? (
        <textarea
          id={id}
          className="event-form-input"
          value={value}
          onChange={onChange}
          placeholder={placeholder}
          required={required}
        />
      ) : (
        <input
          id={id}
          type={type}
          className="event-form-input"
          value={value}
          onChange={onChange}
          placeholder={placeholder}
          required={required}
        />
      )}
    </div>
  );
};

export default EventField;
