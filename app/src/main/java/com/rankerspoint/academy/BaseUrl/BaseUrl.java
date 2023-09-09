package com.rankerspoint.academy.BaseUrl;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

public class BaseUrl {
    public static String MyPREFERENCES = "my_preferences";
    public static final String DEVELOPER_KEY = "AIzaSyDr1U3w_NsjNNGJ24jXpvS2Y60uSfSw7w8";
    public static String BASE_URL = "http://rankerspoint.org/api/Service/";
    public static String BASE_URL_QUIZ = "http://rankerspoint.org/";
    //public static String BASE_URL = "http://allexamtricks.tpwprintyard.com/api/Service/";
    public static String BANN_IMG_URL = "http://rankerspoint.org/ServiceImages/";
    public static String GETALLCATEGORY = BASE_URL+"GetAllCategory";
    public static String GETSUBCATEGORY = BASE_URL+"GetAllSubCategoryByCategory";//CategoryId
    public static String GETPOPUPIMAGE = BASE_URL+"GetAllDynamicImagebyType";//popup,slider
    public static String GETINSTITUTEDETAILS = BASE_URL+"GetInstituteDetails";
    public static String GETALLCOURSEBYSUBCATEGORY = BASE_URL+"GetAllCourseBySubCategory";//SubCategoryId
    public static String GETSINGLECOURSE = BASE_URL+"GetSingleCourseDetails";//CourseId
    public static String GETIMAGESLIDER = BASE_URL+"GetAllDynamicImageByCategory";//CategoryId
    public static String GETALLVIDEOBYCATEGORY = BASE_URL+"GetAllVideoByCategory";//CategoryId
    public static String GETALLSYLLABUSCAT = BASE_URL+"GetAllSubjectByCourse";//CourseId
    public static String GETALLCHAPTERWISE = BASE_URL+"GetAllChapterBySubject";//SubjectId
    public static String GETALLTOPICWISE = BASE_URL+"GetAllTopicByChapter";//ChapterId
    public static String GETALLTOPICWISEVIDEO = BASE_URL+"GetAllVideosDetailsByTopic";//ChapterId
    public static String GETADDDOUBTSDETAILS = BASE_URL+"AddDoubtDetails";//"VideoId, TopicId, ChapterId, SubjectId, CourseId, CategoryId, SubCategoryId,UserId, UserName, UserPic, Pic, Title,Description, Type,Extra1, Extra2, Extra3, Extra4, Extra5"
    public static String GETALLUSERDOUBTS = BASE_URL+"GetAllDoubtByUser";//"UserId,Email

    public static String GETALLDOUBTSCOURSE = BASE_URL+"GetAllDoubtByCourse";//CourseId
    public static String GETSINGLEDOUBTSCOURSE = BASE_URL+"GetSingleDoubtDetails";//doubtid
    public static String ADDANSWERDETAILS = BASE_URL+"AddAnswerDetails";//doubtid
    public static String GETALLANSWERDETAILS = BASE_URL+"GetAllAnswerByDoubt";//doubtid
    public static String GETUSERDETAILS = BASE_URL+"GetUserDetailsByEmail";//email
    public static String UPDATEUSERDETAILS = BASE_URL+"UpdateUserDetails";//put
    public static String UPDATELASTLOGINBYEMAIL = BASE_URL+"UpdateLastLoginByEmail";//put email, date
    public static String GETALLSERIESBYCATEGORY = BASE_URL+"GetAllSeriesByCategory";//put email, date
    public static String GETALLEXAMBYSERIES = BASE_URL+"GetAllExamBySeries";//seriesid
    public static String GETALLQUESTIONBYEXAM = BASE_URL+"GetAllQuestionDetailsByExam";//examid
    public static String SUBMITEXAMRESULTS = BASE_URL+"AddExamResultDetails";//examid
    public static String GETEXAMRESULTSBYUSER = BASE_URL+"GetExamResultDetailsByExamAndUser";//examid
    public static String GETSINGLEALLRESULTS = BASE_URL+"GetSingleExamResultDetails";//examid
    public static String SIGNUP = "http://rankerspoint.org/api/account/Register";//examid
    public static String LOGIN = "http://rankerspoint.org/";//examid
    public static String GETALLSERIESBYTYPE = BASE_URL+"GetAllSeriesByType";//examid
    public static String GETALLSUBJECTOFEXAM = BASE_URL+"GetAllSubjectOfExam";//examid
    public static String GETALLQUESTIONDETAILSBYEXAMANDSUBJECT = BASE_URL+"GetAllQuestionDetailsByExamAndSubject";//examid
    public static String GETSINGLEUSER = BASE_URL+"GetSingleUserDetails";//examid
    public static String GETALLPAYMENTBYUSERANDTYPE = BASE_URL+"GetPaymentByUserAndPurchasedServiceId";//USERID
    public static String GETALLPDFNOTES = BASE_URL+"GetAllPdfNotesDetailsByTopic";//USERID
    public static String GETALLQUIZTOPIC = BASE_URL+"GetAllExamQuizDetailsByTopic";//USERID
    public static String UPDATEDEVICEID = BASE_URL+"UpdateDeviceId1";//USERID
    public static String GETALLNOTESPDF = BASE_URL+"GetAllNotesCategoryByCourseCatId";//USERID
    public static String GETALLNOTESPDFCATEGORY = BASE_URL+"GetAllPdfNotesByCategory";//USERID
    public static String GETALLTOPICVIDEOSERIES = BASE_URL+"GetAllDynamicImageByTypeAndCategory";//{Type}/{ImageCategory}//USERID


    //public static String GETALLTOPICVIDEOSERIES = BASE_URL+"GetAllTop5DynamicImageByType";//USERID

    public static String FORGRTPASSWORD = "http://rankerspoint.org/api/Account/UpdatePassword";//USERID
    public static String GETALLDAILYGK = BASE_URL+"GetAllDailyGK";
    public static String GETSINGLEDAILYGKDETAILS = BASE_URL+"GetSingleDailyGKDetails";
    public static String GETVIDEOSERIESBYCATEGORY = BASE_URL+"GetAllDImageCategoryByCatId";
    public static String GETVIDEOSERIESBYCATEGORYALL = BASE_URL+"GetAllDynamicImagesByDCategory";
    public static String SAVEPAYMENTSTATUS = BASE_URL+"AddServicePaymentDetails";
    public static String UPDATEAPP = BASE_URL+"GetAllDynamicImagebyType/StartScreen";
    public static String UPDATEPAYMENTDETAILS = BASE_URL+"UpdatePaymentDetails";
    public static String GETALLPAYMENTUSER = BASE_URL+"GetAllPaymentByUser";
    public static String GETALLLIVECLASSESCATE = BASE_URL+"GetAllLiveClassByCategory";//categoryid
    public static String GETALLLIVECLASSESBYCOURSE = BASE_URL+"GetAllLiveClassRelatedByCourseAndClass";//categoryid
    public static String GETALLLIVETOPCATEGROY = BASE_URL+"GetAllTopLiveClassByCategory";//categoryid
    public static String GETALLSINGLECLASS = BASE_URL+"GetSingleLiveClassDetails";//categoryid
    public static String SET_CHAT = BASE_URL+"AddLiveComment";//categoryid
    public static String GET_CHAT = BASE_URL+"GetLiveCommentByClass";//categoryid
    public static String APPLYCOUPONCODE = BASE_URL+"GetCouponDetailsByCode";//categoryid
    public static String GETALLPOSTBYCATEGORYMENTOR = BASE_URL+"GetAllPostByCategory";//categoryid
    public static String MENTORPOSTCOMMENT = BASE_URL+"AddPostComment";//categoryid
    public static String GETALLCOMMENTPOSTSHOW = BASE_URL+"GetAllPostCommentByPost";//categoryid
    public static String GETALLPOSTCOMNNETREPLY = BASE_URL+"GetAllPostCommentByReplyCmnt";//categoryid
    public static String GETALLPOSTCOMNNETDELETE = BASE_URL+"DeletePostCommentDetails";//categoryid
    public static String GETSINGLEPOSTCOMMENTS = BASE_URL+"GetSinglePostCommentDetails";//categoryid
    public static String UPDATEPOSTCOMMENT = BASE_URL+"UpdatePostComment";//categoryid
    public static String GETALLFREEVIDEODETAILSBYCOURSE = BASE_URL+"GetAllFreeVideosDetailsByCourse";//categoryid
    public static String GETALLFREEQUIZDETAILSBYCOURSE = BASE_URL+"GetAllFreeExamQuizDetailsByCourse";//categoryid
    public static String ADDSAVENOTESDETAILS = BASE_URL+"AddSaveNoteDetails";//categoryid
    public static String GETALLSAVENOTESCATEGORYANDUSER = BASE_URL+"GetAllSaveNoteByCategoryAndUser";//categoryid
    public static String DELETESAVENOTES = BASE_URL+"DeleteSaveNoteDetails";//categoryid
    public static String GETUPCOMINGCOURSE = BASE_URL+"GetAllUpcomingCourseByCategory";//categoryid
    public static String GETALLSAVENOTESBYCATEGORYUSERANDTYPE = BASE_URL+"GetAllSaveNoteByCategoryUserAndType";//categoryid
    public static String GETTOP10MENTORPOST = BASE_URL+"GetTop10PostByCategory";//categoryid
    public static String GETALLUPCOMINGLIVECLASSBYCAT = BASE_URL+"GetAllUpcomingLiveClassByCategory";//categoryid
    public static String GETRESULTBYUSER = BASE_URL+"GetResultByUserAndExam";//categoryid
    public static String GETALLCOURSEBYCATEGORY = BASE_URL+"GetAllCourseByCategory";//categoryid
    public static String GETALLSERIESBYCOURSEANDTYPE = BASE_URL+"GetAllSeriesByCourseAndType";//categoryid
    public static String DELETEALLEXAMREATTEMPT = BASE_URL+"DeleteAllDynExamQuestDetails";//categoryid
    public static String GETEXAMSTATUSOFUSER = BASE_URL+"GetExamStatusOfUser";//categoryid
    public static String GETEXAMRESULTSBYEXAMSUBJECT = BASE_URL+"GetResultByUserAndExamSubject";//categoryid
    public static String GETHOMERESLUTSALL = BASE_URL+"GetAllExamAndMockResultByUser";//categoryid
    public static String GETALLREPORTCARD = BASE_URL+"GetAllExamAndMockResultByUserAndCourse";//categoryid
    public static String GETSINGLEXAMDETAILS = BASE_URL+"GetSingleExamDetails";//categoryid
    //http://allexamtricksapp21.allexamtricks.com/api/Service/GetAllExamAndMockResultByUserAndCourse
    //username=jaihoinstitute&pass=india6352412010&route=trans1&senderid=JAIHOO&numbers=7309338957&message=123456 is your OTP for verification.
    // http://allexamtricks.tpwprintyard.com/api/Service/GetAllDoubtByCourse/
    //http://allexamtricks.tpwprintyard.com/api/Service/GetAllCourseBySubCategory/{GetAllCourseBySubCategory}
    public static String CREATEORDER_PAY = BASE_URL+"CreateOrder";//categoryid
    public static String COMPLETEORDER_PAY = BASE_URL+"CompleteOrder";//categoryid
    public static String ADDAPPONLINEPAYDETAILS_PAY = BASE_URL+"AddAppOnlinePaymentDetails";//categoryid
    public static String GETSINGLEPAYDETAILS = BASE_URL+"GetSinglePaymentDetails";//categoryid
    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);
            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 }
