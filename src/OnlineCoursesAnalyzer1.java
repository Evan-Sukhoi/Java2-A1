import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.LinkedHashMap;

public class OnlineCoursesAnalyzer1 {

    List<Course> courses;

    public static class Course {

        private String institution;
        private String courseNumber;
        private Date launchDate;
        private String courseTitle;
        private String instructors;
        private Set<String> instructorsSet;

        private String courseSubject;
        private Set<String> courseSubjectSet;
        private int year;
        private int honorCodeCertificates;  // maybe boolean
        private int participants;
        private double audited;
        private double certified;
        private double percentAudited;
        private double percentCertified;
        private double percentCertifiedOfAudited;
        private double percentPlayedVideo;
        private double percentPostedInForum;
        private double percentGradeHigherThanZero;
        private double totalCourseHours;
        private double medianHoursForCertification;
        private double medianAge;
        private double percentMale;
        private double percentFemale;
        private double percentBachelorsDegreeOrHigher;

        @Override
        public String toString() {
            return this.getInstitution()
                + this.getCourseSubjectSet().toString()
                + this.getParticipants();
        }

        public Course(
            String institution,
            String courseNumber,
            Date launchDate,
            String courseTitle,
            String instructors,
            String courseSubject,
            int year,
            int honorCodeCertificates,
            int participants,
            double audited,
            double certified,
            double percentAudited,
            double percentCertified,
            double percentCertifiedOfAudited,
            double percentPlayedVideo,
            double percentPostedInForum,
            double percentGradeHigherThanZero,
            double totalCourseHours,
            double medianHoursForCertification,
            double medianAge,
            double percentMale,
            double percentFemale,
            double percentBachelorsDegreeOrHigher
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

            this.instructors = instructors.replaceAll("\"", "");
            this.instructorsSet = Stream.of(instructors.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
            this.courseSubject = courseSubject.replaceAll("\"", "");
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

        public String getCourseSubject() {
            return courseSubject;
        }
    }

    public OnlineCoursesAnalyzer1(String datasetPath) throws IOException {
//        this.courses = Files.lines(Paths.get(datasetPath))
//            .skip(1)
//            .map(l -> l.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)"))
//            .map(a -> new Course(
//                a[0],
//                a[1],
//                a[2],
//                a[3],
//                a[4],
//                a[5],
//                Integer.parseInt(a[6]),
//                Integer.parseInt(a[7]),
//                Integer.parseInt(a[8]),
//                double.parsedouble(a[9]),
//                double.parsedouble(a[10]),
//                double.parsedouble(a[11]),
//                double.parsedouble(a[12]),
//                double.parsedouble(a[13]),
//                double.parsedouble(a[14]),
//                double.parsedouble(a[15]),
//                double.parsedouble(a[16]),
//                double.parsedouble(a[17]),
//                double.parsedouble(a[18]),
//                double.parsedouble(a[19]),
//                double.parsedouble(a[20]),
//                double.parsedouble(a[21]),
//                double.parsedouble(a[22])
//            ));

        this.courses = new ArrayList<>();
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new FileReader(datasetPath, StandardCharsets.UTF_8));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] info = line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);
                Course course = new Course(info[0], info[1], new Date(info[2]), info[3], info[4],
                    info[5],
                    Integer.parseInt(info[6]), Integer.parseInt(info[7]), Integer.parseInt(info[8]),
                    Integer.parseInt(info[9]), Integer.parseInt(info[10]),
                    Double.parseDouble(info[11]),
                    Double.parseDouble(info[12]), Double.parseDouble(info[13]),
                    Double.parseDouble(info[14]),
                    Double.parseDouble(info[15]), Double.parseDouble(info[16]),
                    Double.parseDouble(info[17]),
                    Double.parseDouble(info[18]), Double.parseDouble(info[19]),
                    Double.parseDouble(info[20]),
                    Double.parseDouble(info[20]), Double.parseDouble(info[21]));
                courses.add(course);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Map<String, Integer> getPtcpCountByInst() {
        Map<String, Integer> map = courses.stream()
            .collect(
                Collectors.groupingBy(
                    Course::getInstitution,
                    Collectors.summingInt(Course::getParticipants)
                )
            );
        return map;
    }

    public Map<String, Integer> getPtcpCountByInstAndSubject() {
        Map<String, Integer> map = courses.stream()
            .collect(
                Collectors.groupingBy(
                    c -> c.getInstitution() + "-" + c.getCourseSubject(),
                    Collectors.summingInt(Course::getParticipants)
                )
            );

        map = map.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                .thenComparing(Map.Entry.comparingByKey()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        return map;
    }


    //3
    public Map<String, List<List<String>>> getCourseListOfInstructor() {
        return null;
    }

    //4
    public List<String> getCourses(int topK, String by) {
        return null;
    }

    //5
    public List<String> searchCourses(String courseSubject, double percentAudited,
        double totalCourseHours) {
        return null;
    }

    //6
    public List<String> recommendCourses(int age, int gender, int isBachelorOrHigher) {
        return null;
    }

    public static void main(String[] args) throws IOException {
        OnlineCoursesAnalyzer1 analyzer = new OnlineCoursesAnalyzer1("resources/local.csv");
        //sort the map by the alphabetical order of the institution.
        analyzer.getPtcpCountByInst().forEach((k, v) -> System.out.println(k + ": " + v));
        analyzer.getPtcpCountByInstAndSubject().forEach((k, v) -> System.out.println(k + ": " + v));

//        System.out.println(analyzer.courses.get(0).getCourseSubject());

//            analyzer.courses.limit(10).forEach(System.out::println);

    }

}
