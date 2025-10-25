import './About.css';


function AboutUs() {
  const teamMembers = [
    { name: 'Talia Crockett', role: 'Developer' },
    { name: 'Daniel Boyce', role: 'Lead Developer' },
    { name: 'Ethan Hillman', role: 'Developer' },
    { name: 'Calvin Robinson', role: 'Developer' },
    { name: 'Joy Brown', role: 'Scrum Master' }
  ];

  return (
    <>
      <div className="about-us-container">
        <div className="about-us-content">
          <div className="about-us-grid">
            {/* Left Column - Group Photo */}
            <div className="team-photo-section">
               <img src="Group_Portrait.jpg" alt="Our Team" className="group-photo" />
              <div className="team-info">
                <h1 className="team-heading">Our Team</h1>
                <div className="team-list">
                  {teamMembers.map((member, index) => (
                    <div key={index} className="team-member-item">
                      <p className="member-name">{member.name}</p>
                      <p className="member-role">{member.role}</p>
                    </div>
                  ))}
                </div>
              </div>
            </div>

            {/* Right Column - About Description */}
            <div className="about-description-section">
              <h2 className="about-heading">About Us</h2>
              <p className="about-text">
                We are a passionate team of developers dedicated to creating innovative 
                solutions and exceptional user experiences. With expertise spanning product 
                management, senior and junior development, and agile methodologies, we work 
                together to bring ideas to life.
              </p>
              <p className="about-text">
                Our collaborative approach and commitment to continuous learning enable us 
                to tackle complex challenges and deliver high-quality results for every project.
              </p>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default AboutUs;