import React from 'react'
import { type FieldProps,type FormData } from '../../types/types';
const Field = ({ label, id, name, value, onChange, error, type = 'text', autoComplete, helpText }:FieldProps) => {
  return (
    <div className="field-container">
      <label htmlFor={id} className="field-label">
        {label}
      </label>
      <input
        id={id}
        name={name}
        type={type}
        value={value}
        onChange={onChange}
        autoComplete={autoComplete}
        aria-invalid={!!error}
        aria-describedby={
          error ? `${id}-error` : helpText ? `${id}-help` : undefined
        }
        required
        className="field-input"
      />
      {helpText && (
        <p id={`${id}-help`} className="field-help">
          {helpText}
        </p>
      )}
      {error && (
        <p id={`${id}-error`} className="field-error">
          {error}
        </p>
      )}
    </div>
  );
}

export default Field
