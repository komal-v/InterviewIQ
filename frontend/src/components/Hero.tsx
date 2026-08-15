import { useState } from 'react'
import './Hero.css'

interface AnalysisResult {
  score: number
  matchedSkills: string[]
  skillGaps: string[]
  requiredSkills: string[]
}

function Hero() {
  const [showAnalysis, setShowAnalysis] = useState(false)

  function handleStartAnalysis() {
    setShowAnalysis(true)
  }


  return (
    <section className="hero">
      {!showAnalysis ? (
        <>
          <p className="hero-label">
            AI-POWERED INTERVIEW PREPARATION
          </p>

          <h1>
            Prepare smarter.
            <br />
            Interview with confidence.
          </h1>

          <p className="hero-description">
            Understand what you need to learn and practice
            for your specific job interview.
          </p>

          <button
            className="hero-button"
            onClick={handleStartAnalysis}
          >
            Start Analysis
          </button>
        </>
      ) : (
        <AnalysisForm />
      )}
    </section>
  )
}

function AnalysisForm() {
  const [jobDescription, setJobDescription] = useState('')

  const [resume, setResume] = useState<File | null>(null)
  
  const [analysisResult, setAnalysisResult] = useState<AnalysisResult | null>(null) 
  const [loading, setLoading] = useState(false)

  function handleJobDescriptionChange(
    event: React.ChangeEvent<HTMLTextAreaElement>
  ) {
    setJobDescription(event.target.value)
  }

function handleResumeChange(
  event: React.ChangeEvent<HTMLInputElement>
) {
  const file = event.target.files?.[0]

  if (!file) {
    return
  }

  const allowedTypes = [
    'application/pdf',
    'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
    'application/msword',
  ]

  const maxSize = 5 * 1024 * 1024 // 5 MB

  if (!allowedTypes.includes(file.type)) {
    alert('Please upload a PDF or DOC/DOCX file.')
    return
  }

  if (file.size > maxSize) {
    alert('Resume must be smaller than 5 MB.')
    return
  }

  setResume(file)
}   

async function handleAnalyze(event: React.FormEvent<HTMLFormElement>) {
  event.preventDefault()
  
  if (!jobDescription.trim()) {
    alert('Please enter a job description.')
    return
  }

  if (!resume) {
    alert('Please upload your resume.')
    return
  }

  const formData = new FormData()
  formData.append('jobDescription', jobDescription)
  formData.append('resume', resume)

  try{
    setLoading(true)
    const response = await fetch('http://localhost:8080/api/analyze', {
      method: 'POST',
      body: formData,
    })

    if (!response.ok) {
      throw new Error('Failed to analyze profile')
    }
    const data: AnalysisResult = await response.json()
    setAnalysisResult(data)
  }catch (error) {
    console.error('Error analyzing profile:', error)

  }
  finally {
    setLoading(false)
  } 
}

  return (
    <>
    <form className="analysis-form" onSubmit={handleAnalyze}>
      <h2>Analyze Your Interview</h2>

      <p>
        Give us the job description and your resume.
        We'll identify your strengths, gaps and likely
        interview questions.
      </p>

      <div className="form-group">
        <label htmlFor="job-description">
          Job Description
        </label>

        <textarea
          id="job-description"
          value={jobDescription}
          onChange={handleJobDescriptionChange}
          placeholder="Paste the job description here..."
          rows={8}
        />

        <small>
          {jobDescription.length} characters
        </small>
      </div>

      <div className="form-group">
        <label>Resume</label>

        <div className="upload-box">
          <p>Upload your resume</p>
          <span>PDF or DOCX</span>

          <input
            type="file"
            accept=".pdf,.doc,.docx"
            onChange={handleResumeChange}
          />
          {resume &&(
            <p className="selected-file">
              Selected file: {resume.name}
              </p>
          )
          }
        </div>
      </div>

      <button className="hero-button"
        type="submit"
        disabled={loading}
      >
        {loading ? 'Analyzing...' : 'Analyze My Profile'}
      </button>
    </form >

    {analysisResult && (
      <div className="analysis-result">

        <h2>Interview Readiness</h2>

        <div className="score">
          {analysisResult.score}%
        </div>

        <h3>Matched Skills</h3>

        <div className="skills">
          {analysisResult.matchedSkills.map((skill) => (
            <span key={skill} className="skill matched">
              ✓ {skill}
            </span>
          ))}
        </div>

        <h3>Skill Gaps</h3>

        <div className="skills">
          {analysisResult.skillGaps.map((skill) => (
            <span key={skill} className="skill gap">
              ⚠ {skill}
            </span>
          ))}
        </div>

      </div>
    )}
    </>
  )
}

export default Hero

