import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OnlineCoursesAnalyzer1 {

    Stream<Course> courses;

    public static class Course {
        private String institution;
        private String courseNumber;
        private String launchDate;
        private String courseTitle;
        private String instructors;
        private Set<String> instructorsSet;

        private String courseSubject;
        private Set<String> courseSubjectSet;
        private int year;
        private int honorCodeCertificates;  // maybe boolean
        private int participants;
        private float audited;
        private float certified;
        private float percentAudited;
        private float percentCertified;
        private float percentCertifiedOfAudited;
        private float percentPlayedVideo;
        private float percentPostedInForum;
        private float percentGradeHigherThanZero;
        private float totalCourseHours;
        private float medianHoursForCertification;
        private float medianAge;
        private float percentMale;
        private float percentFemale;
        private float percentBachelorsDegreeOrHigher;

        @Override
        public String toString(){
            return this.getInstitution()
                + this.getCourseSubjectSet().toString()
                + this.getParticipants();
        }

        public Course(
            String institution,
            String courseNumber,
            String launchDate,
            String courseTitle,
            String instructors,
            String courseSubject,
            int year,
            int honorCodeCertificates,
            int participants,
            float audited,
            float certified,
            float percentAudited,
            float percentCertified,
            float percentCertifiedOfAudited,
            float percentPlayedVideo,
            float percentPostedInForum,
            float percentGradeHigherThanZero,
            float totalCourseHours,
            float medianHoursForCertification,
            float medianAge,
            float percentMale,
            float percentFemale,
            float percentBachelorsDegreeOrHigher
        ) {
            this.institution = institution;
            this.courseNumber = courseNumber;
            this.launchDate = launchDate;
            this.courseTitle = courseTitle;
            this.instructors = instructors;
            this.courseSubject = courseSubject;
            this.year = year;
            this.honorCodeCertificates = honorCodeCertificates;
            this.participants = participants;
            this.audited = audited;
            this.certified = certified;
            this.percentAudited = percentAudited;
            this.percentCertified = percentCertified;
            this.percentCertifiedOfAudited = percentCertifiedOfAudited;
            this.percentPlayedVideo = percentPlayedVideo;
            this.percentPostedInForum = percentPostedInForum;
            this.percentGradeHigherThanZero = percentGradeHigherThanZero;
            this.totalCourseHours = totalCourseHours;
            this.medianHoursForCertification = medianHoursForCertification;
            this.medianAge = medianAge;
            this.percentMale = percentMale;
            this.percentFemale = percentFemale;
            this.percentBachelorsDegreeOrHigher = percentBachelorsDegreeOrHigher;

            instructors = instructors.replaceAll("\"", "");
            this.instructorsSet = Stream.of(instructors.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
            courseSubject = courseSubject.replaceAll("\"", "");
            this.courseSubjectSet = Stream.of(courseSubject.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
        }

        public String getInstitution() {
            return institution;
        }

        public int getParticipants() {
            return participants;
        }

        public Set<String> getCourseSubjectSet() {
//            return courseSubjectSet.toString();
            return courseSubjectSet;
        }

    }

    public OnlineCoursesAnalyzer1(String datasetPath) throws IOException {
        this.courses = Files.lines(Paths.get(datasetPath))
            .skip(1)
            .map(l -> l.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)"))
            .map(a -> new Course(
                a[0],
                a[1],
                a[2],
                a[3],
                a[4],
                a[5],
                Integer.parseInt(a[6]),
                Integer.parseInt(a[7]),
                Integer.parseInt(a[8]),
                Float.parseFloat(a[9]),
                Float.parseFloat(a[10]),
                Float.parseFloat(a[11]),
                Float.parseFloat(a[12]),
                Float.parseFloat(a[13]),
                Float.parseFloat(a[14]),
                Float.parseFloat(a[15]),
                Float.parseFloat(a[16]),
                Float.parseFloat(a[17]),
                Float.parseFloat(a[18]),
                Float.parseFloat(a[19]),
                Float.parseFloat(a[20]),
                Float.parseFloat(a[21]),
                Float.parseFloat(a[22])
            ));

    }

    public TreeMap<String, Integer> getPtcpCountByInst() {
        TreeMap<String, Integer> map = courses.collect(
            Collectors.groupingBy(
                Course::getInstitution,
                TreeMap::new,
                Collectors.summingInt(Course::getParticipants)
            )
        );
        return map;
    }

    public static void main(String[] args) {
        try {
            OnlineCoursesAnalyzer1 analyzer = new OnlineCoursesAnalyzer1("local.csv");
            //sort the map by the alphabetical order of the institution.
            analyzer.getPtcpCountByInst().forEach((k, v) -> System.out.println(k + ": " + v));

//            analyzer.courses.limit(10).forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
