; CS 3372, fall 2021
; Audio generation example: success melody
;
; This Csound file, when given to csbuild.bat, generates audio files.

<CsoundSynthesizer>
<CsOptions>
</CsOptions>
<CsInstruments>

sr     = 44100
ksmps  = 1
nchnls = 2
0dbfs  = 1

giSine ftgen 0, 0, 2^16, 10, 1
giPretty ftgen 0, 0, 2^16, 10, 1, 0.5, 0.75, 0.25
giUgly ftgen 0, 0, 2^16, 10, 0.1, 0.2, 0.3, 0.2, 0.5, 0.2, 0.5, 0.2, 0.1, 0.1

; plain sine instrument
;          p2        p3      p4  p5   p6        p7
; i "sine" startTime noteDur amp freq attackDur decayDur
           instr   sine
p3         =       p3 + p7
iNoteDur   =       p3
iAmp       =       p4
iFreq      =       p5
iAttackDur =       p6
iDecayDur  =       p7
iCurve     =       2
kEnv       transeg 0, iAttackDur, iCurve, iAmp, iNoteDur - iAttackDur - iDecayDur, 0, iAmp, iDecayDur, -iCurve, 0
aOut       poscil3 kEnv, iFreq, giSine
           outs    aOut, aOut
           endin

; more complex instrument
;             p2        p3      p4  p5   p6        p7
; i "complex" startTime noteDur amp freq attackDur decayDur
           instr   complex
p3         =       p3 + p7
iNoteDur   =       p3
iAmp       =       p4
iFreq      =       p5
iAttackDur =       p6
iDecayDur  =       p7
iCurve     =       3
kEnv       transeg 0, iAttackDur, iCurve, iAmp, iNoteDur - iAttackDur - iDecayDur, 0, iAmp, iDecayDur, -iCurve, 0
aOut       poscil3 kEnv, iFreq, giPretty
           outs    aOut, aOut
           endin

</CsInstruments>
<CsScore>
; falling melody:
i "sine" 0.0 0.25 0.2 371.25 0.02 0.25
i "sine" 0.25 0.125 0.2 475.2 0.02 0.25
i "sine" 0.375 0.125 0.2 330.0 0.02 0.25
i "sine" 0.5 0.375 0.2 422.4 0.02 0.25
i "sine" 0.875 0.125 0.2 316.8 0.02 0.25
i "sine" 1.0 0.125 0.2 264.0 0.02 0.25
e

</CsScore>
</CsoundSynthesizer>
