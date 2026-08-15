import FeatureCard from './FeatureCard'
import './Features.css'

function Features() {
  return (
    <section className="features">
      <h2>Everything you need to prepare</h2>

      <p className="features-description">
        InterviewIQ brings your interview preparation into one place.
      </p>

      <div className="feature-grid">
        <FeatureCard
          icon="📄"
          title="Resume Analysis"
          description="Understand how well your resume matches the role."
        />

        <FeatureCard
          icon="💼"
          title="Job Description Analysis"
          description="Identify the skills and technologies the company is looking for."
        />

        <FeatureCard
          icon="🎯"
          title="Interview Preparation"
          description="Generate targeted questions based on the role and your experience."
        />
      </div>
    </section>
  )
}

export default Features