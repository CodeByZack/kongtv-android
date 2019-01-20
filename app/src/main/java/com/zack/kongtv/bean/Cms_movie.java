
package com.zack.kongtv.bean;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class Cms_movie implements Serializable{

    @SerializedName("group_id")
    private Long groupId;
    @SerializedName("type_id")
    private Long typeId;
    @SerializedName("type_id_1")
    private Long typeId1;
    @SerializedName("type_name")
    private String typeName;
    @SerializedName("vod_actor")
    private String vodActor;
    @SerializedName("vod_area")
    private String vodArea;
    @SerializedName("vod_author")
    private String vodAuthor;
    @SerializedName("vod_blurb")
    private String vodBlurb;
    @SerializedName("vod_class")
    private String vodClass;
    @SerializedName("vod_color")
    private String vodColor;
    @SerializedName("vod_content")
    private String vodContent;
    @SerializedName("vod_director")
    private String vodDirector;
    @SerializedName("vod_douban_id")
    private Long vodDoubanId;
    @SerializedName("vod_douban_score")
    private String vodDoubanScore;
    @SerializedName("vod_down")
    private Long vodDown;
    @SerializedName("vod_down_from")
    private String vodDownFrom;
    @SerializedName("vod_down_note")
    private String vodDownNote;
    @SerializedName("vod_down_server")
    private String vodDownServer;
    @SerializedName("vod_down_url")
    private String vodDownUrl;
    @SerializedName("vod_duration")
    private String vodDuration;
    @SerializedName("vod_en")
    private String vodEn;
    @SerializedName("vod_hits")
    private Long vodHits;
    @SerializedName("vod_hits_day")
    private Long vodHitsDay;
    @SerializedName("vod_hits_month")
    private Long vodHitsMonth;
    @SerializedName("vod_hits_week")
    private Long vodHitsWeek;
    @SerializedName("vod_id")
    private Long vodId;
    @SerializedName("vod_isend")
    private Long vodIsend;
    @SerializedName("vod_jumpurl")
    private String vodJumpurl;
    @SerializedName("vod_lang")
    private String vodLang;
    @SerializedName("vod_letter")
    private String vodLetter;
    @SerializedName("vod_level")
    private Long vodLevel;
    @SerializedName("vod_lock")
    private Long vodLock;
    @SerializedName("vod_name")
    private String vodName;
    @SerializedName("vod_pic")
    private String vodPic;
    @SerializedName("vod_pic_slide")
    private String vodPicSlide;
    @SerializedName("vod_pic_thumb")
    private String vodPicThumb;
    @SerializedName("vod_play_from")
    private String vodPlayFrom;
    @SerializedName("vod_play_note")
    private String vodPlayNote;
    @SerializedName("vod_play_server")
    private String vodPlayServer;
    @SerializedName("vod_play_url")
    private String vodPlayUrl;
    @SerializedName("vod_points_down")
    private Long vodPointsDown;
    @SerializedName("vod_points_play")
    private Long vodPointsPlay;
    @SerializedName("vod_pubdate")
    private String vodPubdate;
    @SerializedName("vod_rel_art")
    private String vodRelArt;
    @SerializedName("vod_rel_vod")
    private String vodRelVod;
    @SerializedName("vod_remarks")
    private String vodRemarks;
    @SerializedName("vod_reurl")
    private String vodReurl;
    @SerializedName("vod_score")
    private String vodScore;
    @SerializedName("vod_score_all")
    private Long vodScoreAll;
    @SerializedName("vod_score_num")
    private Long vodScoreNum;
    @SerializedName("vod_serial")
    private String vodSerial;
    @SerializedName("vod_state")
    private String vodState;
    @SerializedName("vod_status")
    private Long vodStatus;
    @SerializedName("vod_sub")
    private String vodSub;
    @SerializedName("vod_tag")
    private String vodTag;
    @SerializedName("vod_time")
    private String vodTime;
    @SerializedName("vod_time_add")
    private Long vodTimeAdd;
    @SerializedName("vod_time_hits")
    private Long vodTimeHits;
    @SerializedName("vod_time_make")
    private Long vodTimeMake;
    @SerializedName("vod_total")
    private Long vodTotal;
    @SerializedName("vod_tpl")
    private String vodTpl;
    @SerializedName("vod_tpl_down")
    private String vodTplDown;
    @SerializedName("vod_tpl_play")
    private String vodTplPlay;
    @SerializedName("vod_trysee")
    private Long vodTrysee;
    @SerializedName("vod_tv")
    private String vodTv;
    @SerializedName("vod_up")
    private Long vodUp;
    @SerializedName("vod_version")
    private String vodVersion;
    @SerializedName("vod_weekday")
    private String vodWeekday;
    @SerializedName("vod_writer")
    private String vodWriter;
    @SerializedName("vod_year")
    private String vodYear;

    private String record;

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getTypeId1() {
        return typeId1;
    }

    public void setTypeId1(Long typeId1) {
        this.typeId1 = typeId1;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getVodActor() {
        return vodActor;
    }

    public void setVodActor(String vodActor) {
        this.vodActor = vodActor;
    }

    public String getVodArea() {
        return vodArea;
    }

    public void setVodArea(String vodArea) {
        this.vodArea = vodArea;
    }

    public String getVodAuthor() {
        return vodAuthor;
    }

    public void setVodAuthor(String vodAuthor) {
        this.vodAuthor = vodAuthor;
    }

    public String getVodBlurb() {
        return vodBlurb;
    }

    public void setVodBlurb(String vodBlurb) {
        this.vodBlurb = vodBlurb;
    }

    public String getVodClass() {
        return vodClass;
    }

    public void setVodClass(String vodClass) {
        this.vodClass = vodClass;
    }

    public String getVodColor() {
        return vodColor;
    }

    public void setVodColor(String vodColor) {
        this.vodColor = vodColor;
    }

    public String getVodContent() {
        return vodContent;
    }

    public void setVodContent(String vodContent) {
        this.vodContent = vodContent;
    }

    public String getVodDirector() {
        return vodDirector;
    }

    public void setVodDirector(String vodDirector) {
        this.vodDirector = vodDirector;
    }

    public Long getVodDoubanId() {
        return vodDoubanId;
    }

    public void setVodDoubanId(Long vodDoubanId) {
        this.vodDoubanId = vodDoubanId;
    }

    public String getVodDoubanScore() {
        return vodDoubanScore;
    }

    public void setVodDoubanScore(String vodDoubanScore) {
        this.vodDoubanScore = vodDoubanScore;
    }

    public Long getVodDown() {
        return vodDown;
    }

    public void setVodDown(Long vodDown) {
        this.vodDown = vodDown;
    }

    public String getVodDownFrom() {
        return vodDownFrom;
    }

    public void setVodDownFrom(String vodDownFrom) {
        this.vodDownFrom = vodDownFrom;
    }

    public String getVodDownNote() {
        return vodDownNote;
    }

    public void setVodDownNote(String vodDownNote) {
        this.vodDownNote = vodDownNote;
    }

    public String getVodDownServer() {
        return vodDownServer;
    }

    public void setVodDownServer(String vodDownServer) {
        this.vodDownServer = vodDownServer;
    }

    public String getVodDownUrl() {
        return vodDownUrl;
    }

    public void setVodDownUrl(String vodDownUrl) {
        this.vodDownUrl = vodDownUrl;
    }

    public String getVodDuration() {
        return vodDuration;
    }

    public void setVodDuration(String vodDuration) {
        this.vodDuration = vodDuration;
    }

    public String getVodEn() {
        return vodEn;
    }

    public void setVodEn(String vodEn) {
        this.vodEn = vodEn;
    }

    public Long getVodHits() {
        return vodHits;
    }

    public void setVodHits(Long vodHits) {
        this.vodHits = vodHits;
    }

    public Long getVodHitsDay() {
        return vodHitsDay;
    }

    public void setVodHitsDay(Long vodHitsDay) {
        this.vodHitsDay = vodHitsDay;
    }

    public Long getVodHitsMonth() {
        return vodHitsMonth;
    }

    public void setVodHitsMonth(Long vodHitsMonth) {
        this.vodHitsMonth = vodHitsMonth;
    }

    public Long getVodHitsWeek() {
        return vodHitsWeek;
    }

    public void setVodHitsWeek(Long vodHitsWeek) {
        this.vodHitsWeek = vodHitsWeek;
    }

    public Long getVodId() {
        return vodId;
    }

    public void setVodId(Long vodId) {
        this.vodId = vodId;
    }

    public Long getVodIsend() {
        return vodIsend;
    }

    public void setVodIsend(Long vodIsend) {
        this.vodIsend = vodIsend;
    }

    public String getVodJumpurl() {
        return vodJumpurl;
    }

    public void setVodJumpurl(String vodJumpurl) {
        this.vodJumpurl = vodJumpurl;
    }

    public String getVodLang() {
        return vodLang;
    }

    public void setVodLang(String vodLang) {
        this.vodLang = vodLang;
    }

    public String getVodLetter() {
        return vodLetter;
    }

    public void setVodLetter(String vodLetter) {
        this.vodLetter = vodLetter;
    }

    public Long getVodLevel() {
        return vodLevel;
    }

    public void setVodLevel(Long vodLevel) {
        this.vodLevel = vodLevel;
    }

    public Long getVodLock() {
        return vodLock;
    }

    public void setVodLock(Long vodLock) {
        this.vodLock = vodLock;
    }

    public String getVodName() {
        return vodName;
    }

    public void setVodName(String vodName) {
        this.vodName = vodName;
    }

    public String getVodPic() {
        return vodPic;
    }

    public void setVodPic(String vodPic) {
        this.vodPic = vodPic;
    }

    public String getVodPicSlide() {
        return vodPicSlide;
    }

    public void setVodPicSlide(String vodPicSlide) {
        this.vodPicSlide = vodPicSlide;
    }

    public String getVodPicThumb() {
        return vodPicThumb;
    }

    public void setVodPicThumb(String vodPicThumb) {
        this.vodPicThumb = vodPicThumb;
    }

    public String getVodPlayFrom() {
        return vodPlayFrom;
    }

    public void setVodPlayFrom(String vodPlayFrom) {
        this.vodPlayFrom = vodPlayFrom;
    }

    public String getVodPlayNote() {
        return vodPlayNote;
    }

    public void setVodPlayNote(String vodPlayNote) {
        this.vodPlayNote = vodPlayNote;
    }

    public String getVodPlayServer() {
        return vodPlayServer;
    }

    public void setVodPlayServer(String vodPlayServer) {
        this.vodPlayServer = vodPlayServer;
    }

    public String getVodPlayUrl() {
        return vodPlayUrl;
    }

    public void setVodPlayUrl(String vodPlayUrl) {
        this.vodPlayUrl = vodPlayUrl;
    }

    public Long getVodPointsDown() {
        return vodPointsDown;
    }

    public void setVodPointsDown(Long vodPointsDown) {
        this.vodPointsDown = vodPointsDown;
    }

    public Long getVodPointsPlay() {
        return vodPointsPlay;
    }

    public void setVodPointsPlay(Long vodPointsPlay) {
        this.vodPointsPlay = vodPointsPlay;
    }

    public String getVodPubdate() {
        return vodPubdate;
    }

    public void setVodPubdate(String vodPubdate) {
        this.vodPubdate = vodPubdate;
    }

    public String getVodRelArt() {
        return vodRelArt;
    }

    public void setVodRelArt(String vodRelArt) {
        this.vodRelArt = vodRelArt;
    }

    public String getVodRelVod() {
        return vodRelVod;
    }

    public void setVodRelVod(String vodRelVod) {
        this.vodRelVod = vodRelVod;
    }

    public String getVodRemarks() {
        return vodRemarks;
    }

    public void setVodRemarks(String vodRemarks) {
        this.vodRemarks = vodRemarks;
    }

    public String getVodReurl() {
        return vodReurl;
    }

    public void setVodReurl(String vodReurl) {
        this.vodReurl = vodReurl;
    }

    public String getVodScore() {
        return vodScore;
    }

    public void setVodScore(String vodScore) {
        this.vodScore = vodScore;
    }

    public Long getVodScoreAll() {
        return vodScoreAll;
    }

    public void setVodScoreAll(Long vodScoreAll) {
        this.vodScoreAll = vodScoreAll;
    }

    public Long getVodScoreNum() {
        return vodScoreNum;
    }

    public void setVodScoreNum(Long vodScoreNum) {
        this.vodScoreNum = vodScoreNum;
    }

    public String getVodSerial() {
        return vodSerial;
    }

    public void setVodSerial(String vodSerial) {
        this.vodSerial = vodSerial;
    }

    public String getVodState() {
        return vodState;
    }

    public void setVodState(String vodState) {
        this.vodState = vodState;
    }

    public Long getVodStatus() {
        return vodStatus;
    }

    public void setVodStatus(Long vodStatus) {
        this.vodStatus = vodStatus;
    }

    public String getVodSub() {
        return vodSub;
    }

    public void setVodSub(String vodSub) {
        this.vodSub = vodSub;
    }

    public String getVodTag() {
        return vodTag;
    }

    public void setVodTag(String vodTag) {
        this.vodTag = vodTag;
    }

    public String getVodTime() {
        return vodTime;
    }

    public void setVodTime(String vodTime) {
        this.vodTime = vodTime;
    }

    public Long getVodTimeAdd() {
        return vodTimeAdd;
    }

    public void setVodTimeAdd(Long vodTimeAdd) {
        this.vodTimeAdd = vodTimeAdd;
    }

    public Long getVodTimeHits() {
        return vodTimeHits;
    }

    public void setVodTimeHits(Long vodTimeHits) {
        this.vodTimeHits = vodTimeHits;
    }

    public Long getVodTimeMake() {
        return vodTimeMake;
    }

    public void setVodTimeMake(Long vodTimeMake) {
        this.vodTimeMake = vodTimeMake;
    }

    public Long getVodTotal() {
        return vodTotal;
    }

    public void setVodTotal(Long vodTotal) {
        this.vodTotal = vodTotal;
    }

    public String getVodTpl() {
        return vodTpl;
    }

    public void setVodTpl(String vodTpl) {
        this.vodTpl = vodTpl;
    }

    public String getVodTplDown() {
        return vodTplDown;
    }

    public void setVodTplDown(String vodTplDown) {
        this.vodTplDown = vodTplDown;
    }

    public String getVodTplPlay() {
        return vodTplPlay;
    }

    public void setVodTplPlay(String vodTplPlay) {
        this.vodTplPlay = vodTplPlay;
    }

    public Long getVodTrysee() {
        return vodTrysee;
    }

    public void setVodTrysee(Long vodTrysee) {
        this.vodTrysee = vodTrysee;
    }

    public String getVodTv() {
        return vodTv;
    }

    public void setVodTv(String vodTv) {
        this.vodTv = vodTv;
    }

    public Long getVodUp() {
        return vodUp;
    }

    public void setVodUp(Long vodUp) {
        this.vodUp = vodUp;
    }

    public String getVodVersion() {
        return vodVersion;
    }

    public void setVodVersion(String vodVersion) {
        this.vodVersion = vodVersion;
    }

    public String getVodWeekday() {
        return vodWeekday;
    }

    public void setVodWeekday(String vodWeekday) {
        this.vodWeekday = vodWeekday;
    }

    public String getVodWriter() {
        return vodWriter;
    }

    public void setVodWriter(String vodWriter) {
        this.vodWriter = vodWriter;
    }

    public String getVodYear() {
        return vodYear;
    }

    public void setVodYear(String vodYear) {
        this.vodYear = vodYear;
    }

}
