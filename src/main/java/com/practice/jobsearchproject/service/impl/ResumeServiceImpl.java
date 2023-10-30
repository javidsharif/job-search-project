package com.practice.jobsearchproject.service.impl;

import com.practice.jobsearchproject.exception.NotFoundException;
import com.practice.jobsearchproject.model.dto.request.ResumeRequestDto;
import com.practice.jobsearchproject.model.dto.response.InsideResumeDto;
import com.practice.jobsearchproject.model.dto.response.PublicResumeResponse;
import com.practice.jobsearchproject.model.dto.response.ResumeResponseDto;
import com.practice.jobsearchproject.model.entity.*;
import com.practice.jobsearchproject.model.mapper.ResumeMapper;
import com.practice.jobsearchproject.repository.*;
import com.practice.jobsearchproject.service.ResumeService;
import com.practice.jobsearchproject.utils.Updatable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final PrefferedWorkRepository prefferedWorkRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final EducationRepository educationRepository;
    private final KnowledgeRepository knowledgeRepository;
    private final LanguageRepository languageRepository;
    private final AchievementAndAwardRepository achievementAndAwardRepository;
    private final ResumeMapper resumeMapper;

    @Override
    public List<ResumeResponseDto> getCandidateResumes(Long id) {
        List<Resume> resumes = resumeRepository.findAllByUserId(id);
        return resumeMapper.toResumeResponse(resumes);
    }

    @Override
    public InsideResumeDto findResumeById(Long id) {
        Resume resume = resumeRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found"));
        return getInsideResumeDto(resume);
    }

    @Override
    public void createResume(User user, ResumeRequestDto resumeRequestDto) {
        Resume resume = resumeMapper.toResume(resumeRequestDto);
        resume.setCount(0L);
        PrefferedWork prefferedWork = resumeMapper.toPrefferedWork(resumeRequestDto.getPrefferedWork());
        prefferedWork.setResume(resume);
        resume.setUser(user);
        resumeRepository.save(resume);
        prefferedWorkRepository.save(prefferedWork);
        List<Education> educations = resumeMapper.toEducationList(resumeRequestDto.getEducations());
        educations.forEach(education -> education.setResume(resume));
        educationRepository.saveAll(educations);
        List<Language> languages = resumeMapper.toLanguageList(resumeRequestDto.getLanguages());
        languages.forEach(language -> language.setResume(resume));
        languageRepository.saveAll(languages);
        List<Knowledge> knowledgeList = resumeMapper.toKnowledgeList(resumeRequestDto.getKnowledgeList());
        knowledgeList.forEach(knowledge -> knowledge.setResume(resume));
        knowledgeRepository.saveAll(knowledgeList);
        List<AchievementAndAward> achievementAndAwardList = resumeMapper.toAchievementAndAwardList(resumeRequestDto.getAchievementAndAwards());
        achievementAndAwardList.forEach(achievementAndAward -> achievementAndAward.setResume(resume));
        achievementAndAwardRepository.saveAll(achievementAndAwardList);
        List<WorkExperience> workExperienceList = resumeMapper.toWorkExperienceList(resumeRequestDto.getWorkExperiences());
        workExperienceList.forEach(workExperience -> workExperience.setResume(resume));
        workExperienceRepository.saveAll(workExperienceList);

        resume.setRegistrationDate(LocalDateTime.now());
        resume.setUpdateDate(LocalDateTime.now());

    }

    @Override
    public void updateResume(Long id, ResumeRequestDto resumeRequestDto) {
        fillResume(id, resumeRequestDto);
    }

    @Override
    @Transactional
    public void removeResumeById(Long id) {
        if(resumeRepository.findById(id).isPresent()) {
            prefferedWorkRepository.deleteByResumeId(id);
            educationRepository.deleteAllByResumeId(id);
            achievementAndAwardRepository.deleteAllByResumeId(id);
            workExperienceRepository.deleteAllByResumeId(id);
            languageRepository.deleteAllByResumeId(id);
            knowledgeRepository.deleteAllByResumeId(id);
            resumeRepository.deleteById(id);
        }else {
            throw new NotFoundException(String.format("resume with id %s doesn't exists", id));
        }

    }

    @Override
    public List<PublicResumeResponse> getAllResumes() {
        List<Resume> resumes = resumeRepository.findAll();
        List<PublicResumeResponse> list = new ArrayList<>();
        resumes.forEach(resume -> {
            if(resume.getIsPostCv()) {
                PrefferedWork prefferedWork = prefferedWorkRepository.findByResumeId(resume.getId());
                PublicResumeResponse response = new PublicResumeResponse(
                        resume.getNameAndSurname(), resume.getPosition(),
                        prefferedWork.getAreaInWhichYouWork(), prefferedWork.getPrefferedSalary(),
                        resume.getUser().getPhotoUrl(), resume.getUpdateDate());
                list.add(response);
            }
        });
        return list;
    }

    @Override
    public InsideResumeDto findPublicResumeById(Long id) {
        Resume resume = resumeRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found"));
        resume.setCount(resume.getCount() + 1);
        resumeRepository.save(resume);
        return getInsideResumeDto(resume);
    }

    private InsideResumeDto getInsideResumeDto(Resume resume) {
        InsideResumeDto insideResumeDto = resumeMapper.toInsideResume(resume);
        insideResumeDto.setPrefferedWork(resumeMapper.toPrefferedWork(prefferedWorkRepository.findById(resume.getId())
                .orElse(new PrefferedWork())));
        insideResumeDto.setWorkExperiences(resumeMapper.toWorkExperienceResponseList(
                workExperienceRepository.findAllByResumeId(resume.getId())));
        insideResumeDto.setEducations(resumeMapper.toEducationResponseList(
                educationRepository.findAllByResumeId(resume.getId())));
        insideResumeDto.setKnowledgeList(resumeMapper.toKnowledgeResponseList(
                knowledgeRepository.findAllByResumeId(resume.getId())));
        insideResumeDto.setLanguages(resumeMapper.toLanguageResponseList(
                languageRepository.findAllByResumeId(resume.getId())));
        insideResumeDto.setAchievementAndAwards(resumeMapper.toAchievementAndAwardResponseList(
                achievementAndAwardRepository.findAllByResumeId(resume.getId())));
        return insideResumeDto;
    }

    public void fillResume(Long id, ResumeRequestDto resumeRequestDto) {
        Resume resume = resumeRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found"));
        resume.updateResume(resumeRequestDto);
        PrefferedWork prefferedWork = prefferedWorkRepository.findByResumeId(id);
        prefferedWork.updatePrefferedWork(resumeRequestDto.getPrefferedWork());
        prefferedWorkRepository.save(prefferedWork);
        List<WorkExperience> workExperienceList = workExperienceRepository.findAllByResumeId(id);
        updateList(workExperienceList, resumeRequestDto.getWorkExperiences(), workExperienceRepository);
        List<AchievementAndAward> achievementAndAwards = achievementAndAwardRepository.findAllByResumeId(id);
        updateList(achievementAndAwards, resumeRequestDto.getAchievementAndAwards(), achievementAndAwardRepository);
        List<Language> languages = languageRepository.findAllByResumeId(id);
        updateList(languages, resumeRequestDto.getLanguages(), languageRepository);
        List<Knowledge> knowledgeList = knowledgeRepository.findAllByResumeId(id);
        updateList(knowledgeList, resumeRequestDto.getKnowledgeList(), knowledgeRepository);
        List<Education> educations = educationRepository.findAllByResumeId(id);
        updateList(educations, resumeRequestDto.getEducations(), educationRepository);
        resume.setUpdateDate(LocalDateTime.now());
        resumeRepository.save(resume);
    }

    private <T extends Updatable, R extends JpaRepository<T, ?>> void updateList(List<T> existingList, List<?> updatedList, R repository) {
        for (int i = 0; i < existingList.size(); i++) {
            existingList.get(i).update(updatedList.get(i));
            repository.save(existingList.get(i));
        }
    }


}