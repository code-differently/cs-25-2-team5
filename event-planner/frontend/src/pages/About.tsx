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
    <div className="min-h-screen bg-gradient-to-br from-slate-50 to-slate-100">
      {/* Header */}
      <header className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-6 py-8">
          <h1 className="text-4xl font-bold text-slate-800 text-center">About Us</h1>
        </div>
      </header>

      {/* Team Section */}
      <section className="max-w-5xl mx-auto px-6 py-16">
        <div className="bg-white rounded-lg shadow-lg p-8">
          <h2 className="text-3xl font-bold text-cyan-500 text-center mb-8 border-b-2 border-cyan-500 pb-4">
            Meet the Team!
          </h2>
          
          <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
            {teamMembers.map((member, index) => (
              <div key={index} className="text-center">
                {/* Placeholder for image */}
                <div className="w-full aspect-square bg-gradient-to-br from-cyan-400 to-blue-500 rounded-lg flex items-center justify-center mb-3 shadow-md">
                  <User className="w-16 h-16 text-white opacity-50" />
                </div>
                
                {/* Member info */}
                <div>
                  <h3 className="font-bold text-slate-800 text-sm mb-1">{member.name}</h3>
                  <p className="text-slate-600 text-xs">{member.role}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>
    </div>
  );
}