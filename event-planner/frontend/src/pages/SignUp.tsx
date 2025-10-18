// src/app/(auth)/signup/page.tsx
'use client';

import { useState } from 'react';
import Link from 'next/link';

type FormData = {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  confirmPassword: string;
};

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
  const [submitting, setSubmitting] = useState(false);
  const [successMsg, setSuccessMsg] = useState<string | null>(null);

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target as { name: keyof FormData; value: string };
    setForm((f) => ({ ...f, [name]: value }));
    setErrors((e) => ({ ...e, [name]: undefined, form: undefined }));
  };

  const validate = (data: FormData): FormErrors => {
    const e: FormErrors = {};
    if (!data.firstName.trim()) e.firstName = 'First name is required';
    if (!data.lastName.trim()) e.lastName = 'Last name is required';
    
    // More robust email validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(data.email)) e.email = 'Enter a valid email';
    
    if (data.password.length < 8) e.password = 'Password must be at least 8 characters';
    if (data.password !== data.confirmPassword) e.confirmPassword = 'Passwords do not match';
    
    return e;
  };

  const onSubmit = async (evt: React.FormEvent) => {
    evt.preventDefault();
    setSuccessMsg(null);

    const v = validate(form);
    if (Object.keys(v).length) {
      setErrors(v);
      return;
    }

    try {
      setSubmitting(true);
      // TODO: swap this with your real API endpoint
      // Example POST:
      // const res = await fetch('/api/auth/signup', {
      //   method: 'POST',
      //   headers: { 'Content-Type': 'application/json' },
      //   body: JSON.stringify(form),
      // });
      // if (!res.ok) throw new Error('Failed to sign up');

      // For now, just simulate success:
      await new Promise((r) => setTimeout(r, 600));
      setSuccessMsg('Thanks! Your signup has been recorded.');
      setForm({ firstName: '', lastName: '', email: '', password: '', confirmPassword: '' });
    } catch (err: unknown) {
      setErrors({ form: (err as Error)?.message || 'Something went wrong' });
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div>
      <form
        onSubmit={onSubmit}
        noValidate
        className="w-full max-w-md rounded-2xl border border-zinc-200 dark:border-zinc-800 shadow-sm p-6 bg-white dark:bg-zinc-900"
        aria-describedby={errors.form ? 'form-error' : undefined}
      >
        <h1 className="text-2xl font-bold mb-4">Sign Up</h1>

        {errors.form && (
          <p id="form-error" className="mb-3 text-sm text-red-600">
            {errors.form}
          </p>
        )}
        {successMsg && (
          <p role="status" className="mb-3 text-sm text-green-600">
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
          className="mt-2 w-full h-11 rounded-xl font-semibold bg-black text-white dark:bg-white dark:text-black disabled:opacity-60 disabled:cursor-not-allowed"
        >
          {submitting ? 'Submitting…' : 'Sign Up'}
        </button>
        
        <p className="mt-4 text-sm text-center text-gray-600 dark:text-gray-400">
          Already have an account?{' '}
          <Link href="/login" className="text-blue-600 hover:underline dark:text-blue-400">
            Log in
          </Link>
        </p>
      </form>
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
    <div className="mb-4">
      <label htmlFor={id} className="block text-sm font-medium mb-1">
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
        className="w-full rounded-xl border border-zinc-300 dark:border-zinc-700 bg-transparent px-3 py-2 outline-none focus:ring-2 focus:ring-zinc-400"
      />
      {helpText && (
        <p id={`${id}-help`} className="mt-1 text-xs text-gray-600 dark:text-gray-400">
          {helpText}
        </p>
      )}
      {error && (
        <p id={`${id}-error`} className="mt-1 text-xs text-red-600">
          {error}
        </p>
      )}
    </div>
  );
}