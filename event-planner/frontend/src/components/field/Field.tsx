import React from 'react'

type FieldProps =  {
  label: string;
  id: string;
  name: keyof FormData;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  error?: string;
  type?: string;
  autoComplete?: string;
  helpText?: string;
}
const Field = ({ label, id, name, value, onChange, error, type = 'text', autoComplete, helpText }:FieldProps) => {
  return (
    <div>
      
    </div>
  )
}

export default Field
