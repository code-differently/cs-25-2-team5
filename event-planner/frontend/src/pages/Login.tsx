import React, { useState } from 'react';
import './Login.css';

export const Login: React.FC = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        const loginData = {
            email: email,
            password: password,
        };

        console.log('Login attempt:', loginData);

        // TODO: Replace with actual login logic when database is ready
        alert('Login form submitted successfully!');

        // Reset form
        setEmail('');
        setPassword('');
    };

    return (
        <article>
            <section className="login-page">
                {/* Left side - Login Form */}
                <div className="login-form-section">
                    <div className="login-container">
                        <div className="login-header">
                            <h1 className="login-welcome">Welcome back!</h1>
                            <p className="login-subtitle">Sign in to plan smarter and celebrate better.</p>
                        </div>

                        <form onSubmit={handleSubmit} className="login-form">
                            <div className="form-group">
                                <label htmlFor="email">Email:</label>
                                <input
                                    type="email"
                                    id="email"
                                    value={email}
                                    onChange={e => setEmail(e.target.value)}
                                    placeholder="Ex. Janedoe@example.com"
                                    required
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="password">Password:</label>
                                <input
                                    type="password"
                                    id="password"
                                    value={password}
                                    onChange={e => setPassword(e.target.value)}
                                    placeholder="Enter password"
                                    required
                                />
                            </div>

                            <button type="submit" className="login-btn">
                                Sign In
                            </button>

                            <a href="#" className="forgot-password-link">
                                Forgot password?
                            </a>
                        </form>
                    </div>
                </div>

                {/* Right side - Image */}
                <div className="login-image-section">
                    <div className="login-image-content">
                        <img 
                            src="/flowers.jpg" 
                            alt="flowers in a vase" 
                            className="login-hero-image"
                        />
                        <div className="login-image-text">
                            <h2>Plan Smarter</h2>
                            <p>Create memorable events with our intuitive planning tools</p>
                        </div>
                    </div>
                </div>
            </section>
        </article>
    );
}

export default Login;