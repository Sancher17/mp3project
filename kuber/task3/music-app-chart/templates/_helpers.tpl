{{/* Current date */}}
{{- define "current-date" -}}
{{- now | htmlDate }}
{{- end -}}

{{/* Version */}}
{{- define "version-label" -}}
{{- printf "version=%s" .Chart.AppVersion }}
{{- end -}}