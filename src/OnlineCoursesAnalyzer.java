import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.LinkedHashMap;
import java.util.HashMap;


public class OnlineCoursesAnalyzer {

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

        public Course(Course course) {
            this.institution = course.institution;
            this.courseNumber = course.courseNumber;
            this.launchDate = course.launchDate;
            this.courseTitle = course.courseTitle;
            this.instructors = course.instructors;
            this.instructorsSet = course.instructorsSet;
            this.courseSubjectSet = course.courseSubjectSet;
            this.courseSubject = course.courseSubject;
            this.year = course.year;
            this.honorCodeCertificates = course.honorCodeCertificates;
            this.participants = course.participants;
            this.audited = course.audited;
            this.certified = course.certified;
            this.percentAudited = course.percentAudited;
            this.percentCertified = course.percentCertified;
            this.percentCertifiedOfAudited = course.percentCertifiedOfAudited;
            this.percentPlayedVideo = course.percentPlayedVideo;
            this.percentPostedInForum = course.percentPostedInForum;
            this.percentGradeHigherThanZero = course.percentGradeHigherThanZero;
            this.totalCourseHours = course.totalCourseHours;
            this.medianHoursForCertification = course.medianHoursForCertification;
            this.medianAge = course.medianAge;
            this.percentMale = course.percentMale;
            this.percentFemale = course.percentFemale;
            this.percentBachelorsDegreeOrHigher = course.percentBachelorsDegreeOrHigher;
        }

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

            this.courseTitle = courseTitle.replaceAll("\"", "");

            this.instructors = instructors.replaceAll("\"", "");
            this.instructorsSet = Stream.of(this.instructors.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());

            this.courseSubject = courseSubject.replaceAll("\"", "");
            String tem = this.courseSubject.replaceAll("and ", "");
            tem = tem.replaceAll(" ", ",");
            this.courseSubjectSet = Stream.of(tem.split(","))
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
            return courseSubjectSet;
        }

        public String getCourseSubject() {
            return courseSubject;
        }

        public Set<String> getInstructorsSet() {
            return instructorsSet;
        }

        public String getInstructors() {
            return instructors;
        }

        public String getCourseTitle() {
            return courseTitle;
        }

        public double getTotalCourseHours() {
            return totalCourseHours;
        }

        public double getPercentAudited() {
            return percentAudited;
        }

        public String getCourseNumber() {
            return courseNumber;
        }

        public Date getLaunchDate() {
            return launchDate;
        }

        public double getPercentMale() {
            return percentMale;
        }

        public void setInstructors(String instructor) {
            this.instructors = instructor;
        }

        public double getMedianAge() {
            return medianAge;
        }

        public double getPercentBachelorsDegreeOrHigher(){
            return percentBachelorsDegreeOrHigher;
        }
    }

    public OnlineCoursesAnalyzer(String datasetPath) throws IOException {
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
        Map<String, List<List<String>>> map = new HashMap<>();

        // First, group courses by instructor name
        Map<String, List<Course>> coursesByInstructor = courses.stream()
            .flatMap(course -> course.getInstructorsSet().stream()
                .map(instructor -> {
                    Course copy = new Course(course);
                    copy.setInstructors(instructor);
                    return copy;
                })
            )
            .collect(Collectors.groupingBy(Course::getInstructors));

        // For each instructor, create a list of their independently responsible and co-developed courses
        for (Map.Entry<String, List<Course>> entry : coursesByInstructor.entrySet()) {
            List<Course> courses = entry.getValue();

            // Create a set to hold course titles and lists to hold independently responsible and co-developed courses
            Set<String> independentCourses = new HashSet<>();
            Set<String> coDevelopedCourses = new HashSet<>();

            // Sort courses by course title and add unique course titles to the appropriate list
            courses.stream()
                .sorted(Comparator.comparing(Course::getCourseTitle))
                .forEach(course -> {
                    String courseTitle = course.getCourseTitle();
                    if (course.getInstructorsSet().size() == 1 && !independentCourses.contains(
                        courseTitle)) {
                        independentCourses.add(courseTitle);
                    } else if (!coDevelopedCourses.contains(courseTitle)
                        && course.getInstructorsSet().size() != 1) {
                        coDevelopedCourses.add(courseTitle);
                    }
                });

            // Add instructor and course lists to map
            List<List<String>> courseLists = new ArrayList<>();
            courseLists.add(new ArrayList<>(independentCourses).stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList()));
            courseLists.add(new ArrayList<>(coDevelopedCourses).stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList()));
            map.put(entry.getKey(), courseLists);
        }

        return map;
    }


    //4
    public List<String> getCourses(int topK, String by) {
        switch (by) {
            case "hours":
                return courses.stream()
                    .sorted(Comparator.comparing(Course::getTotalCourseHours).reversed())
                    .map(Course::getCourseTitle)
                    .distinct()
                    .limit(topK)
                    .collect(Collectors.toList());
            case "participants":
                return courses.stream()
                    .sorted(Comparator.comparing(Course::getParticipants).reversed())
                    .map(Course::getCourseTitle)
                    .distinct()
                    .limit(topK)
                    .collect(Collectors.toList());
            default:
                return null;
        }
    }

    //5
    public List<String> searchCourses(String courseSubject, double percentAudited, double totalCourseHours) {
        return courses.stream()
            .filter(course -> course.getCourseSubjectSet().stream().anyMatch(subject -> subject.equalsIgnoreCase(courseSubject)))
            .filter(course -> course.getPercentAudited() >= percentAudited)
            .filter(course -> course.getTotalCourseHours() <= totalCourseHours)
            .map(Course::getCourseTitle)
            .distinct()
            .sorted(Comparator.naturalOrder())
            .collect(Collectors.toList());
    }

    //6
    public List<String> recommendCourses(int age, int gender, int isBachelorOrHigher) {
        Map<String, Double> map = courses.stream()
            .collect(Collectors.groupingBy(Course::getCourseNumber,
                Collectors.mapping(
                    course -> Arrays.asList(course.getMedianAge(), course.getPercentMale(), course.getPercentBachelorsDegreeOrHigher()),
                    Collectors.toList())))
            .entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey,
                entry -> {
                    double[] averages = IntStream.range(0, 3) // 获取每一列
                        .mapToDouble(column -> entry.getValue().stream() // 将每一行映射为这一列的元素
                            .mapToDouble(row -> row.get(column))
                            .average()
                            .orElse(0.0))
                        .toArray(); // 将平均值放到数组中

                    return Math.pow(age - averages[0], 2) +
                        Math.pow((gender * 100) - averages[1], 2) +
                        Math.pow((isBachelorOrHigher * 100) - averages[2], 2);
                }));

        List<String> topCourses = map.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue())
            .limit(10)
            .map(Map.Entry::getKey)
            .toList();

        System.out.println(topCourses);

        // print the similarity of the top 10 courses
//        topCourses.forEach(courseNumber -> System.out.println(courseNumber + ": " + map.get(courseNumber)));
//        System.out.println();
//        System.out.println(map.get("4.605x"));
//        System.out.println(map.get("14.73x"));

        return topCourses.stream()
            .map(courseNumber -> courses.stream()
                .filter(course -> course.getCourseNumber().equals(courseNumber))
                .max(Comparator.comparing(Course::getLaunchDate))
                .get())
            .map(Course::getCourseTitle)
            .distinct()
            .collect(Collectors.toList());
    }

    public static void main(String[] args) throws IOException {
        OnlineCoursesAnalyzer analyzer = new OnlineCoursesAnalyzer("resources/local.csv");
        //sort the map by the alphabetical order of the institution.
//        analyzer.getPtcpCountByInst().forEach((k, v) -> System.out.println(k + ": " + v));
//        analyzer.getPtcpCountByInstAndSubject().forEach((k, v) -> System.out.println(k + ": " + v));
//        analyzer.getCourseListOfInstructor().forEach((k, v) -> System.out.println(k + " == " + v));

//        analyzer.searchCourses("computer", 20.0, 700).forEach(System.out::println);

        analyzer.recommendCourses(25, 1, 1).forEach(System.out::println);
//        System.out.println(analyzer.courses.get(183).getInstructorsSet().toString());


//            analyzer.courses.limit(10).forEach(System.out::println);

    }

}
