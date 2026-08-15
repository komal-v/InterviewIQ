import './Navbar.css'

function Navbar() {
  return (
    <nav className="navbar">
      <div className="logo">
        InterviewIQ
      </div>

      <div className="nav-links">
        <a href="#">Home</a>
        <a href="#">How It Works</a>
        <a href="#">Features</a>

        <button className="sign-in">
          Sign In
        </button>
      </div>
    </nav>
  )
}

export default Navbar