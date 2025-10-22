// src/app/(auth)/signup/page.tsx
'use client';

import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useSignUp } from '@clerk/clerk-react';
import { useNavigate } from 'react-router-dom';
import './SignUp.css';
import React from 'react';

type FormData = {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  confirmPassword: string;
};

type FormErrors = Partial<Record<keyof FormData, string>> & { form?: string };

export default function SignUpPage() {

  const { isLoaded, signUp , setActive } = useSignUp();
  const [errors, setErrors] = useState<FormErrors>({});
  const [submitting, setSubmitting] = useState(false);
  const [successMsg, setSuccessMsg] = useState<string | null>(null);
  const [verifying, setVerifying] = useState(false)
  const [code, setCode] = React.useState('')
  const navigate = useNavigate()
  const [form, setForm] = useState<FormData>({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: '',
  });

  type FormData = {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  confirmPassword: string;
};
  if (!isLoaded) return <div>Loading...</div>

    // Start the sign-up process using the email and password provided
  //   const handleSignUp = async() => {

  //       try {
  //       await signUp.create({
  //         emailAddress:form.email,
  //         password:form.password,
  //       })

  //       // Send the user an email with the verification code
  //       await signUp.prepareEmailAddressVerification({
  //         strategy: 'email_code',
  //       })

  //       // Set 'verifying' true to display second form
  //       // and capture the OTP code
  //       setVerifying(true)
  //     } catch (err: any) {
  //       // See https://clerk.com/docs/guides/development/custom-flows/error-handling
  //       // for more info on error handling
  //       console.error(JSON.stringify(err, null, 2))
  //     }

  //   };

  //   const handleVerification = async(e:React.FormEvent)=>  {
  //       e.preventDefault();
  //       if (!isLoaded) return <div>Loading...</div>
  //       try {
  //     // Use the code the user provided to attempt verification
  //     const signUpAttempt = await signUp.attemptEmailAddressVerification({
  //       code,
  //     })

  //     // If verification was completed, set the session to active
  //     // and redirect the user
  //     if (signUpAttempt.status === 'complete') {
  //       await setActive({
  //         session: signUpAttempt.createdSessionId,
  //         navigate: async ({ session }) => {
  //           if (session?.currentTask) {
  //             // Check for session tasks and navigate to custom UI to help users resolve them
  //             // See https://clerk.com/docs/guides/development/custom-flows/overview#session-tasks
  //             console.log(session?.currentTask)
  //             router.push('/sign-up/tasks')
  //             return
  //           }

  //           router.push('/')
  //         },
  //       })
  //     } else {
  //       // If the status is not complete, check why. User may need to
  //       // complete further steps.
  //       console.error('Sign-up attempt not complete:', signUpAttempt)
  //       console.error('Sign-up attempt status:', signUpAttempt.status)
  //     }
  //   } catch (err: any) {
  //     // See https://clerk.com/docs/guides/development/custom-flows/error-handling
  //     // for more info on error handling
  //     console.error(JSON.stringify(err, null, 2))
  //   }
  // }
      
    
}


    


  
  

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target as { name: keyof FormData; value: string };
    setForm((f) => ({ ...f, [name]: value }));
    setErrors((e) => ({ ...e, [name]: undefined, form: undefined }));
  };

  



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
            onSubmit={onSubmit}
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

function Field(props: {
  label: string;
  id: string;
  name: keyof FormData;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  error?: string;
  type?: string;
  autoComplete?: string;
  helpText?: string;
}) {
  const { label, id, name, value, onChange, error, type = 'text', autoComplete, helpText } = props;
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