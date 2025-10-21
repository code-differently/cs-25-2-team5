import { User } from 'lucide-react';

export default function AboutUs() {
  const teamMembers = [
    { name: 'Talia Crockett', role: 'Product Owner' },
    { name: 'Daniel Boyce', role: 'Senior Developer' },
    { name: 'Ethan Hillman', role: 'Junior Developer' },
    { name: 'Calvin Robinson', role: 'Junior Developer' },
    { name: 'Joy Brown', role: 'Scrum Master' }
  ];

  return (
    <div className="about-us-container">
      {/* Header */}
      <header className="header">
        <div className="header-content">
          <h1 className="main-title">About Us</h1>
        </div>
      </header>

      {/* Team Section */}
      <section className="team-section">
        <div className="team-container">
          <h2 className="team-title">
            Meet the Team!
          </h2>
          
          <div className="team-grid">
            {teamMembers.map((member, index) => (
              <div key={index} className="member-card">
                {/* Placeholder for image */}
                <div className="member-avatar">
                  <User className="user-icon" />
                </div>
                
                {/* Member info */}
                <div className="member-info">
                  <h3 className="member-name">{member.name}</h3>
                  <p className="member-role">{member.role}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>
    </div>
  );
}