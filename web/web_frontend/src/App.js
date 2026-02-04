import React, { useState } from 'react';

const Register = () => {
    const [user, setUser] = useState({ username: '', email: '', password: '' });

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("Form Submitted:", user);
        alert("Registration Successful (Simulated)!");
    };

    return (
        <div style={{ padding: '20px', textAlign: 'center' }}>
            <h2>Create Account</h2>
            <form onSubmit={handleSubmit}>
                <input type="text" placeholder="Username" onChange={(e) => setUser({...user, username: e.target.value})} /><br/><br/>
                <input type="email" placeholder="Email" onChange={(e) => setUser({...user, email: e.target.value})} /><br/><br/>
                <input type="password" placeholder="Password" onChange={(e) => setUser({...user, password: e.target.value})} /><br/><br/>
                <button type="submit">Register</button>
            </form>
        </div>
    );
};

export default Register;