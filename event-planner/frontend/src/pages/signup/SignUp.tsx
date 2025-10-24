

import { useState } from 'react';
import { Link } from 'react-router-dom';
import './SignUp.css';
import { useSignUp } from '@clerk/clerk-react';
import React from 'react';
import { useNavigate } from 'react-router-dom';
import Field from '../../components/field/Field';
import { type FormData } from '../../types/types';

type FormErrors = Partial<Record<keyof FormData, string>> & { form?: string };

export default function SignUpPage() {
  const [form, setForm] = useState<FormData>({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: '',
  });
  const [errors, setErrors] = useState<FormErrors>({});
  const [submitting] = useState(false);
  const [successMsg] = useState<string | null>(null);
  const { isLoaded, signUp, setActive } = useSignUp();
  const navigate = useNavigate();

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target as { name: keyof FormData; value: string };
    setForm((f) => ({ ...f, [name]: value }));
    setErrors((e) => ({ ...e, [name]: undefined, form: undefined }));
  };
  
  const API_URL = import.meta.env.VITE_API_URL;
  const handleAPICall = async () => {
      await fetch(`${API_URL}/users`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        
        body: JSON.stringify({
          name: form.firstName + ' ' + form.lastName,
          email: form.email,
          password: form.password,
          
          
        }
      )
      });
    }
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()

    if (!isLoaded) return <div>Loading...</div>
    
    // Start the sign-up process using the email and password provided
    try {
     const response =  await signUp.create({
        emailAddress:form.email,
        password:form.password
      })
      if(response.status === 'complete'){

        const apiResponse = await handleAPICall();
        console.log(apiResponse);
        await setActive({
          session: response.createdSessionId,
          navigate: async () => {
            navigate('/')
          },
        })
      }
        


      // Send the user an email with the verification code
     

      // Set 'verifying' true to display second form
      // and capture the OTP code
      
    } catch (err: any) {
      // See https://clerk.com/docs/guides/development/custom-flows/error-handling
      // for more info on error handling
      console.error(JSON.stringify(err, null, 2))
    }
  }



  return (
    <div className="signup-page">
      {/* Left side - Image Section */}
      <div className="signup-image-section">
        <div className="signup-image-content">
          <img 
            src="/photoshoot.jpg" 
            alt="Colorful photoshoot setup" 
            className="signup-hero-image"
          />
          <div className="signup-image-text">
            <h2>Join Our Community</h2>
            <p>Create unforgettable events with our comprehensive planning tools</p>
          </div>
        </div>
      </div>

      {/* Right side - Form Section */}
      <div className="signup-form-section">
        <div className="signup-container">
          <form
            onSubmit={handleSubmit}
            noValidate
            className="signup-form"
            aria-describedby={errors.form ? 'form-error' : undefined}
          >
            <h1 className="signup-title">Sign Up</h1>

            {errors.form && (
              <p id="form-error" className="form-error">
                {errors.form}
              </p>
            )}
            {successMsg && (
              <p role="status" className="success-message">
                {successMsg}
              </p>
            )}

        <Field
          label="First name"
          id="firstName"
          name="firstName"
          value={form.firstName}
          onChange={onChange}
          error={errors.firstName}
          autoComplete="given-name"
        />

        <Field
          label="Last name"
          id="lastName"
          name="lastName"
          value={form.lastName}
          onChange={onChange}
          error={errors.lastName}
          autoComplete="family-name"
        />

        <Field
          label="Email"
          id="email"
          name="email"
          type="email"
          value={form.email}
          onChange={onChange}
          error={errors.email}
          autoComplete="email"
        />

        <Field
          label="Password"
          id="password"
          name="password"
          type="password"
          value={form.password}
          onChange={onChange}
          error={errors.password}
          autoComplete="new-password"
          helpText="Password must be at least 8 characters long"
        />

        <Field
          label="Confirm Password"
          id="confirmPassword"
          name="confirmPassword"
          type="password"
          value={form.confirmPassword}
          onChange={onChange}
          error={errors.confirmPassword}
          autoComplete="new-password"
        />

        <div id="clerk-captcha" />

        <button
          type="submit"
          disabled={submitting}
          className="submit-button"
        >
          {submitting ? 'Submitting…' : 'Sign Up'}
        </button>
        
        <p className="login-link-text">
          Already have an account?{' '}
          <Link to="/login" className="login-link">
            Log in
          </Link>
        </p>
          </form>
        </div>
      </div>
    </div>
  );
}