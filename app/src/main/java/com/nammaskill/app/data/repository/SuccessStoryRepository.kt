package com.nammaskill.app.data.repository

import com.nammaskill.app.data.model.SkillCenter
import com.nammaskill.app.data.model.SuccessStory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SuccessStoryRepository @Inject constructor() {

    /**
     * Returns a list of dummy success stories for the MVP.
     */
    fun getSuccessStories(): List<SuccessStory> = listOf(
        SuccessStory(
            id = "story_001",
            name = "Ramesh Kumar",
            age = 24,
            courseCompleted = "Advanced Electrician Training",
            jobAchieved = "Maintenance Engineer at Infosys Campus, Bangalore",
            testimonial = "Coming from a small village near Raichur, I never imagined I'd work at a top IT company. The electrician course gave me the skills and confidence. Now I earn ₹25,000/month and support my family.",
            location = "Raichur, Karnataka",
            completionYear = 2025
        ),
        SuccessStory(
            id = "story_002",
            name = "Lakshmi Devi",
            age = 28,
            courseCompleted = "Fashion Design & Embroidery",
            jobAchieved = "Owner of 'Lakshmi Boutique' in Mysore",
            testimonial = "After the tailoring course, I started with one sewing machine at home. Today, I have my own boutique with 3 employees! Women from my village now come to learn from me.",
            location = "Mandya, Karnataka",
            completionYear = 2024
        ),
        SuccessStory(
            id = "story_003",
            name = "Arjun Shetty",
            age = 22,
            courseCompleted = "Full-Stack Web Development",
            jobAchieved = "Junior Developer at TCS, Hubli",
            testimonial = "I was working at a small shop after 12th. The coding bootcamp changed my life. I built my first website in just 2 weeks. Now I develop enterprise applications at TCS.",
            location = "Haveri, Karnataka",
            completionYear = 2025
        ),
        SuccessStory(
            id = "story_004",
            name = "Priya Mahadevappa",
            age = 20,
            courseCompleted = "Mobile Phone Repair Technician",
            jobAchieved = "Service Center Technician at Samsung Authorized Centre",
            testimonial = "My father is a farmer. No one in my family had a technical job before. This 1-month course opened doors I didn't know existed. I'm now saving to open my own repair shop.",
            location = "Bellary, Karnataka",
            completionYear = 2025
        ),
        SuccessStory(
            id = "story_005",
            name = "Suresh Basavaraj",
            age = 30,
            courseCompleted = "Solar Panel Installation",
            jobAchieved = "Solar Installation Supervisor at Tata Power Solar",
            testimonial = "I was a daily wage laborer before. The solar training course was only 2 months, but it gave me lifetime skills. Solar is the future, and I'm proud to be part of India's green energy revolution.",
            location = "Gulbarga, Karnataka",
            completionYear = 2024
        ),
        SuccessStory(
            id = "story_006",
            name = "Kavitha Naik",
            age = 26,
            courseCompleted = "Data Entry & Office Skills",
            jobAchieved = "Data Entry Operator at District Collector's Office",
            testimonial = "I didn't even know how to use a computer before the course. Now I work in a government office and help digitize important records. My parents are so proud!",
            location = "Shimoga, Karnataka",
            completionYear = 2025
        )
    )

    /**
     * Returns dummy skill centers for the map view.
     */
    fun getSkillCenters(): List<SkillCenter> = listOf(
        SkillCenter(
            id = "center_001",
            name = "Karnataka Skill Development Centre",
            latitude = 12.9907,
            longitude = 77.5667,
            address = "Rajajinagar Industrial Area, Bangalore - 560010",
            phone = "+91 80 2332 1000",
            coursesOffered = listOf("Electrician", "Coding", "Mobile Repair"),
            rating = 4.5f
        ),
        SkillCenter(
            id = "center_002",
            name = "Mysore Women's Skill Hub",
            latitude = 12.3051,
            longitude = 76.6551,
            address = "Devaraja Mohalla, Mysore - 570001",
            phone = "+91 821 242 1000",
            coursesOffered = listOf("Sewing", "Fashion Design"),
            rating = 4.3f
        ),
        SkillCenter(
            id = "center_003",
            name = "Digital India Skill Centre",
            latitude = 15.3647,
            longitude = 75.1240,
            address = "Vidyanagar, Hubli - 580021",
            phone = "+91 836 235 1000",
            coursesOffered = listOf("Coding", "Data Entry"),
            rating = 4.7f
        ),
        SkillCenter(
            id = "center_004",
            name = "Coastal Skill Foundation",
            latitude = 12.8698,
            longitude = 74.8431,
            address = "Hampankatta, Mangalore - 575001",
            phone = "+91 824 242 1000",
            coursesOffered = listOf("Mobile Repair", "Electrician"),
            rating = 4.2f
        ),
        SkillCenter(
            id = "center_005",
            name = "North Karnataka Fashion Institute",
            latitude = 15.8497,
            longitude = 74.4977,
            address = "Camp Area, Belgaum - 590001",
            phone = "+91 831 246 1000",
            coursesOffered = listOf("Sewing", "Fashion Design", "Embroidery"),
            rating = 4.4f
        ),
        SkillCenter(
            id = "center_006",
            name = "Gulbarga Renewable Energy Centre",
            latitude = 17.3297,
            longitude = 76.8343,
            address = "Sedam Road, Gulbarga - 585105",
            phone = "+91 847 227 1000",
            coursesOffered = listOf("Electrician", "Solar Installation"),
            rating = 4.1f
        ),
        SkillCenter(
            id = "center_007",
            name = "Bangalore Digital Academy",
            latitude = 12.8456,
            longitude = 77.6603,
            address = "Electronic City Phase 1, Bangalore - 560100",
            phone = "+91 80 4125 1000",
            coursesOffered = listOf("Coding", "Android Development", "Web Development"),
            rating = 4.8f
        )
    )
}
