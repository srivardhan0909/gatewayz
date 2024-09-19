import React from 'react';

const HomePage = () => {
  return (
    <div className="bg-gradient-to-b from-purple-900 to-blue-900 min-h-screen flex justify-center items-center">
      <div className="max-w-5xl px-6 py-12 w-full">
        {/* Header and Navigation */}
        
        {/* Main Content */}
        <div className="flex items-center justify-between">
          {/* Left Content */}
          <div className="text-white">
            <h1 className="text-4xl font-extrabold leading-tight">
              welcome to <span className="text-indigo-400 text-7xl">Gatewayz</span>
            </h1>
            <p className="mt-6 text-xl">
              A Platform for innovation and collaboration with unique ideas.
            </p>
            <div className="mt-12 space-x-6">
              <button className="bg-black text-white px-6 py-3 rounded-full hover:bg-gray-800">Start Learning</button>
            </div>
          </div>

          {/* Right Image */}
          <div className="ml-8">
            <img
              className="w-full max-w-md rounded-lg"
              src="src/assets/homepage.png"
              alt="Homepage"
            />
          </div>
        </div>

        {/* 3D Design Illustration */}
        <div className="mt-12">
          {/* Placeholder for 3D Objects */}
          <div className="flex justify-center items-center space-x-6">
            <div className="bg-indigo-700 p-6 rounded-xl shadow-lg"></div>
            <div className="bg-indigo-500 p-4 rounded-xl shadow-lg"></div>
            <div className="bg-indigo-300 p-3 rounded-full shadow-lg"></div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default HomePage;
