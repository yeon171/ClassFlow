document.addEventListener('DOMContentLoaded', () => {
    // 회원가입 폼
    const registrationForm = document.getElementById('registrationForm');
    const regName = document.getElementById('regName');
    const regEmail = document.getElementById('regEmail');
    const regPassword = document.getElementById('regPassword');
    const registrationResult = document.getElementById('registrationResult');

    // 로그인 폼
    const loginForm = document.getElementById('loginForm');
    const loginEmail = document.getElementById('loginEmail');
    const loginPassword = document.getElementById('loginPassword');
    const loginResult = document.getElementById('loginResult');

    // ID로 조회 폼
    const lookupIdForm = document.getElementById('lookupIdForm');
    const lookupId = document.getElementById('lookupId');
    const lookupIdResult = document.getElementById('lookupIdResult');

    // 이메일로 조회 폼
    const lookupEmailForm = document.getElementById('lookupEmailForm');
    const lookupEmail = document.getElementById('lookupEmail');
    const lookupEmailResult = document.getElementById('lookupEmailResult');

    // 예약 생성 폼
    const createReservationForm = document.getElementById('createReservationForm');
    const resMemberId = document.getElementById('resMemberId');
    const resDateTime = document.getElementById('resDateTime');
    const createReservationResult = document.getElementById('createReservationResult');

    // 회원별 예약 목록 조회 폼
    const listReservationsForm = document.getElementById('listReservationsForm');
    const listResMemberId = document.getElementById('listResMemberId');
    const listReservationsResult = document.getElementById('listReservationsResult');

    // 예약 취소 폼
    const cancelReservationForm = document.getElementById('cancelReservationForm');
    const cancelResId = document.getElementById('cancelResId');
    const cancelReservationResult = document.getElementById('cancelReservationResult');


    // 결과 표시 함수
    function displayResult(element, data, isError = false) {
        element.innerHTML = isError ? `오류: ${data}` : JSON.stringify(data, null, 2);
        element.className = `result ${isError ? 'error' : 'success'}`;
    }

    // 회원가입 제출 핸들러
    registrationForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const memberData = {
            name: regName.value,
            email: regEmail.value,
            password: regPassword.value
        };

        try {
            const response = await fetch('/api/members/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(memberData)
            });

            const text = await response.text(); // 응답이 JSON이 아닐 수 있으므로 text로 받음

            if (response.ok) {
                displayResult(registrationResult, text);
                // 폼 초기화
                regName.value = '';
                regEmail.value = '';
                regPassword.value = '';
            } else {
                displayResult(registrationResult, text, true);
            }
        } catch (error) {
            displayResult(registrationResult, error.message, true);
        }
    });

    // 로그인 제출 핸들러
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const loginData = {
            email: loginEmail.value,
            password: loginPassword.value
        };

        try {
            const response = await fetch('/api/members/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(loginData)
            });

            const data = await response.json(); // 로그인 API는 JSON 응답을 기대

            if (response.ok) {
                displayResult(loginResult, data);
                // 폼 초기화
                loginEmail.value = '';
                loginPassword.value = '';
            } else {
                displayResult(loginResult, data.message || '로그인 실패', true);
            }
        } catch (error) {
            displayResult(loginResult, error.message, true);
        }
    });

    // ID로 조회 제출 핸들러
    lookupIdForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const id = lookupId.value;

        try {
            const response = await fetch(`/api/members/${id}`);
            const data = await response.json();

            if (response.ok) {
                displayResult(lookupIdResult, data);
            } else {
                displayResult(lookupIdResult, data.message || '회원을 찾을 수 없습니다.', true);
            }
        } catch (error) {
            displayResult(lookupIdResult, error.message, true);
        }
    });

    // 이메일로 조회 제출 핸들러
    lookupEmailForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const email = lookupEmail.value;

        try {
            const response = await fetch(`/api/members/email/${email}`);
            const data = await response.json();

            if (response.ok) {
                displayResult(lookupEmailResult, data);
            } else {
                displayResult(lookupEmailResult, data.message || '회원을 찾을 수 없습니다.', true);
            }
        } catch (error) {
            displayResult(lookupEmailResult, error.message, true);
        }
    });

    // 예약 생성 제출 핸들러
    createReservationForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const reservationData = {
            memberId: resMemberId.value,
            dateTime: resDateTime.value // "YYYY-MM-DDTHH:MM" 형식
        };

        try {
            const response = await fetch('/api/reservations', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(reservationData)
            });

            const data = await response.json();

            if (response.ok) {
                displayResult(createReservationResult, data);
                resMemberId.value = '';
                resDateTime.value = '';
            } else {
                displayResult(createReservationResult, data.message || '예약 생성 실패', true);
            }
        } catch (error) {
            displayResult(createReservationResult, error.message, true);
        }
    });

    // 회원별 예약 목록 조회 제출 핸들러
    listReservationsForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const memberId = listResMemberId.value;

        try {
            const response = await fetch(`/api/reservations/member/${memberId}`);
            const data = await response.json();

            if (response.ok) {
                displayResult(listReservationsResult, data);
            } else {
                displayResult(listReservationsResult, data.message || '예약 목록 조회 실패', true);
            }
        } catch (error) {
            displayResult(listReservationsResult, error.message, true);
        }
    });

    // 예약 취소 제출 핸들러
    cancelReservationForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const reservationId = cancelResId.value;

        try {
            const response = await fetch(`/api/reservations/${reservationId}/cancel`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            const data = await response.json();

            if (response.ok) {
                displayResult(cancelReservationResult, data);
                cancelResId.value = '';
            } else {
                displayResult(cancelReservationResult, data.message || '예약 취소 실패', true);
            }
        } catch (error) {
            displayResult(cancelReservationResult, error.message, true);
        }
    });
});
